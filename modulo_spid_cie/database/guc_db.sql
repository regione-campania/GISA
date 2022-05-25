--
-- PostgreSQL database dump
--

-- Dumped from database version 10.16
-- Dumped by pg_dump version 12.9

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: spid; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA spid;


ALTER SCHEMA spid OWNER TO postgres;

--
-- Name: dblink; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS dblink WITH SCHEMA public;


--
-- Name: EXTENSION dblink; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION dblink IS 'connect to other PostgreSQL databases from within a database';


--
-- Name: postgres_fdw; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS postgres_fdw WITH SCHEMA public;


--
-- Name: EXTENSION postgres_fdw; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgres_fdw IS 'foreign-data wrapper for remote PostgreSQL servers';


--
-- Name: guc_ruolo; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.guc_ruolo AS (
	id_ruolo integer,
	descrizione_ruolo text,
	end_point text
);


ALTER TYPE public.guc_ruolo OWNER TO postgres;

--
-- Name: OBSdbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."OBSdbi_insert_utente_guc"(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
msg text ;
us_id int ;
ruolo_gisa guc_ruolo;
ruolo_gisa_ext guc_ruolo;
ruolo_bdu guc_ruolo;
ruolo_vam guc_ruolo;
ruolo_importatori guc_ruolo;
ruolo_digemon guc_ruolo;

BEGIN


--Inserisco utente
us_id:= (select nextval('guc_utenti_id_seq'));
INSERT INTO guc_utenti_ (ID,CODICE_FISCALE,COGNOME,EMAIL,ENABLED,entered,enteredby,expires,modified,modifiedby,note,nome,password,username,asl_id,password_encrypted,canile_id, canile_description,luogo,num_autorizzazione,id_importatore,canilebdu_id, canilebdu_description,importatori_description,id_provincia_iscrizione_albo_vet_privato,nr_iscrione_albo_vet_privato,id_utente_guc_old,suap_ip_address ,suap_istat_comune ,suap_pec ,suap_callback ,suap_shared_key,suap_callback_ko,num_registrazione_stab,suap_livello_accreditamento,suap_descrizione_livello_accreditamento,telefono  ) 
values (us_id,input_cf,input_cognome,input_email,input_enabled,now(),input_enteredby,input_expires,now(),input_modifiedby,input_note, input_nome,input_password,input_username,input_asl_id,input_password_encrypted,input_canile_id, input_canile_description,input_luogo,input_num_autorizzazione,input_id_importatore,input_canilebdu_id, input_canilebdu_description,input_importatori_description,input_id_provincia_iscrizione_albo_vet_privato,input_nr_iscrione_albo_vet_privato,input_id_utente_guc_old,input_suap_ip_address ,input_suap_istat_comune ,input_suap_pec ,input_suap_callback ,input_suap_shared_key,input_suap_callback_ko,input_num_registrazione_stab,input_suap_livello_accreditamento,input_suap_descrizione_livello_accreditamento,input_telefono  );

msg:='OK';


--Popolo ruoli e li inserisco

ruolo_gisa.id_ruolo:= input_ruolo_id_gisa;
ruolo_gisa.descrizione_ruolo:= input_ruolo_descrizione_gisa;
ruolo_gisa.end_point:= 'Gisa';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa.end_point,ruolo_gisa.id_ruolo,ruolo_gisa.descrizione_ruolo,us_id,input_note);

ruolo_gisa_ext.id_ruolo:= input_ruolo_id_gisa_ext;
ruolo_gisa_ext.descrizione_ruolo:= input_ruolo_descrizione_gisa_ext;
ruolo_gisa_ext.end_point:= 'Gisa_ext';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa_ext.end_point,ruolo_gisa_ext.id_ruolo,ruolo_gisa_ext.descrizione_ruolo,us_id,input_note);

ruolo_bdu.id_ruolo:= input_ruolo_id_bdu;
ruolo_bdu.descrizione_ruolo:= input_ruolo_descrizione_bdu;
ruolo_bdu.end_point:= 'bdu';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_bdu.end_point,ruolo_bdu.id_ruolo,ruolo_bdu.descrizione_ruolo,us_id,input_note);

ruolo_vam.id_ruolo:= input_ruolo_id_vam;
ruolo_vam.descrizione_ruolo:= input_ruolo_descrizione_vam;
ruolo_vam.end_point:= 'Vam';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_vam.end_point,ruolo_vam.id_ruolo,ruolo_vam.descrizione_ruolo,us_id,input_note);

ruolo_importatori.id_ruolo:= input_ruolo_id_importatori;
ruolo_importatori.descrizione_ruolo:= input_ruolo_descrizione_importatori;
ruolo_importatori.end_point:= 'Importatori';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_importatori.end_point,ruolo_importatori.id_ruolo,ruolo_importatori.descrizione_ruolo,us_id,input_note);

ruolo_digemon.id_ruolo:= input_ruolo_id_digemon;
ruolo_digemon.descrizione_ruolo:= input_ruolo_descrizione_digemon;
ruolo_digemon.end_point:= 'Digemon';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_digemon.end_point,ruolo_digemon.id_ruolo,ruolo_digemon.descrizione_ruolo,us_id,input_note);

	
	
	RETURN msg||'_'||us_id;

END
$$;


ALTER FUNCTION public."OBSdbi_insert_utente_guc"(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text) OWNER TO postgres;

--
-- Name: OBSdbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."OBSdbi_insert_utente_guc"(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text, input_luogo_vam text, input_id_provincia_vam integer, input_nr_iscrizione_vam text, input_gestore_acque integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
msg text ;
us_id int ;
ruolo_gisa guc_ruolo;
ruolo_gisa_ext guc_ruolo;
ruolo_bdu guc_ruolo;
ruolo_vam guc_ruolo;
ruolo_importatori guc_ruolo;
ruolo_digemon guc_ruolo;

BEGIN


--Inserisco utente
us_id:= (select nextval('guc_utenti_id_seq'));
INSERT INTO guc_utenti_ (ID,CODICE_FISCALE,COGNOME,EMAIL,ENABLED,entered,enteredby,expires,modified,modifiedby,note,nome,password,username,asl_id,password_encrypted,canile_id, canile_description,luogo,num_autorizzazione,id_importatore,canilebdu_id, canilebdu_description,importatori_description,id_provincia_iscrizione_albo_vet_privato,nr_iscrione_albo_vet_privato,id_utente_guc_old,suap_ip_address ,suap_istat_comune ,suap_pec ,suap_callback ,suap_shared_key,suap_callback_ko,num_registrazione_stab,suap_livello_accreditamento,suap_descrizione_livello_accreditamento,telefono, luogo_vam, id_provincia_iscrizione_albo_vet_privato_vam, nr_iscrione_albo_vet_privato_vam, gestore_acque  ) 
values (us_id,input_cf,input_cognome,input_email,input_enabled,now(),input_enteredby,input_expires,now(),input_modifiedby,input_note, input_nome,input_password,input_username,input_asl_id,input_password_encrypted,input_canile_id, input_canile_description,input_luogo,input_num_autorizzazione,input_id_importatore,input_canilebdu_id, input_canilebdu_description,input_importatori_description,input_id_provincia_iscrizione_albo_vet_privato,input_nr_iscrione_albo_vet_privato,input_id_utente_guc_old,input_suap_ip_address ,input_suap_istat_comune ,input_suap_pec ,input_suap_callback ,input_suap_shared_key,input_suap_callback_ko,input_num_registrazione_stab,input_suap_livello_accreditamento,input_suap_descrizione_livello_accreditamento,input_telefono , input_luogo_vam, input_id_provincia_vam, input_nr_iscrizione_vam, input_gestore_acque );

msg:='OK';


--Popolo ruoli e li inserisco

ruolo_gisa.id_ruolo:= input_ruolo_id_gisa;
ruolo_gisa.descrizione_ruolo:= input_ruolo_descrizione_gisa;
ruolo_gisa.end_point:= 'Gisa';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa.end_point,ruolo_gisa.id_ruolo,ruolo_gisa.descrizione_ruolo,us_id,input_note);

ruolo_gisa_ext.id_ruolo:= input_ruolo_id_gisa_ext;
ruolo_gisa_ext.descrizione_ruolo:= input_ruolo_descrizione_gisa_ext;
ruolo_gisa_ext.end_point:= 'Gisa_ext';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa_ext.end_point,ruolo_gisa_ext.id_ruolo,ruolo_gisa_ext.descrizione_ruolo,us_id,input_note);

ruolo_bdu.id_ruolo:= input_ruolo_id_bdu;
ruolo_bdu.descrizione_ruolo:= input_ruolo_descrizione_bdu;
ruolo_bdu.end_point:= 'bdu';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_bdu.end_point,ruolo_bdu.id_ruolo,ruolo_bdu.descrizione_ruolo,us_id,input_note);

ruolo_vam.id_ruolo:= input_ruolo_id_vam;
ruolo_vam.descrizione_ruolo:= input_ruolo_descrizione_vam;
ruolo_vam.end_point:= 'Vam';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_vam.end_point,ruolo_vam.id_ruolo,ruolo_vam.descrizione_ruolo,us_id,input_note);

ruolo_importatori.id_ruolo:= input_ruolo_id_importatori;
ruolo_importatori.descrizione_ruolo:= input_ruolo_descrizione_importatori;
ruolo_importatori.end_point:= 'Importatori';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_importatori.end_point,ruolo_importatori.id_ruolo,ruolo_importatori.descrizione_ruolo,us_id,input_note);

ruolo_digemon.id_ruolo:= input_ruolo_id_digemon;
ruolo_digemon.descrizione_ruolo:= input_ruolo_descrizione_digemon;
ruolo_digemon.end_point:= 'Digemon';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_digemon.end_point,ruolo_digemon.id_ruolo,ruolo_digemon.descrizione_ruolo,us_id,input_note);

	
	
	RETURN msg||'_'||us_id;

END
$$;


ALTER FUNCTION public."OBSdbi_insert_utente_guc"(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text, input_luogo_vam text, input_id_provincia_vam integer, input_nr_iscrizione_vam text, input_gestore_acque integer) OWNER TO postgres;

--
-- Name: OBSdbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public."OBSdbi_insert_utente_guc"(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text, input_luogo_vam text, input_id_provincia_vam integer, input_nr_iscrizione_vam text, input_gestore_acque integer, input_comune_gestore_acque integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
msg text ;
us_id int ;
ruolo_gisa guc_ruolo;
ruolo_gisa_ext guc_ruolo;
ruolo_bdu guc_ruolo;
ruolo_vam guc_ruolo;
ruolo_importatori guc_ruolo;
ruolo_digemon guc_ruolo;

BEGIN


--Inserisco utente
us_id:= (select nextval('guc_utenti_id_seq'));
INSERT INTO guc_utenti_ (ID,CODICE_FISCALE,COGNOME,EMAIL,ENABLED,entered,enteredby,expires,modified,modifiedby,note,nome,password,username,asl_id,password_encrypted,canile_id, canile_description,luogo,num_autorizzazione,id_importatore,canilebdu_id, canilebdu_description,importatori_description,id_provincia_iscrizione_albo_vet_privato,nr_iscrione_albo_vet_privato,id_utente_guc_old,suap_ip_address ,suap_istat_comune ,suap_pec ,suap_callback ,suap_shared_key,suap_callback_ko,num_registrazione_stab,suap_livello_accreditamento,suap_descrizione_livello_accreditamento,telefono, luogo_vam, id_provincia_iscrizione_albo_vet_privato_vam, nr_iscrione_albo_vet_privato_vam, gestore_acque , comune_gestore_acque ) 
values (us_id,input_cf,input_cognome,input_email,input_enabled,now(),input_enteredby,input_expires,now(),input_modifiedby,input_note, input_nome,input_password,input_username,input_asl_id,input_password_encrypted,input_canile_id, input_canile_description,input_luogo,input_num_autorizzazione,input_id_importatore,input_canilebdu_id, input_canilebdu_description,input_importatori_description,input_id_provincia_iscrizione_albo_vet_privato,input_nr_iscrione_albo_vet_privato,input_id_utente_guc_old,input_suap_ip_address ,input_suap_istat_comune ,input_suap_pec ,input_suap_callback ,input_suap_shared_key,input_suap_callback_ko,input_num_registrazione_stab,input_suap_livello_accreditamento,input_suap_descrizione_livello_accreditamento,input_telefono , input_luogo_vam, input_id_provincia_vam, input_nr_iscrizione_vam, input_gestore_acque, input_comune_gestore_acque );

msg:='OK';


--Popolo ruoli e li inserisco

ruolo_gisa.id_ruolo:= input_ruolo_id_gisa;
ruolo_gisa.descrizione_ruolo:= input_ruolo_descrizione_gisa;
ruolo_gisa.end_point:= 'Gisa';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa.end_point,ruolo_gisa.id_ruolo,ruolo_gisa.descrizione_ruolo,us_id,input_note);

ruolo_gisa_ext.id_ruolo:= input_ruolo_id_gisa_ext;
ruolo_gisa_ext.descrizione_ruolo:= input_ruolo_descrizione_gisa_ext;
ruolo_gisa_ext.end_point:= 'Gisa_ext';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa_ext.end_point,ruolo_gisa_ext.id_ruolo,ruolo_gisa_ext.descrizione_ruolo,us_id,input_note);

ruolo_bdu.id_ruolo:= input_ruolo_id_bdu;
ruolo_bdu.descrizione_ruolo:= input_ruolo_descrizione_bdu;
ruolo_bdu.end_point:= 'bdu';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_bdu.end_point,ruolo_bdu.id_ruolo,ruolo_bdu.descrizione_ruolo,us_id,input_note);

ruolo_vam.id_ruolo:= input_ruolo_id_vam;
ruolo_vam.descrizione_ruolo:= input_ruolo_descrizione_vam;
ruolo_vam.end_point:= 'Vam';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_vam.end_point,ruolo_vam.id_ruolo,ruolo_vam.descrizione_ruolo,us_id,input_note);

ruolo_importatori.id_ruolo:= input_ruolo_id_importatori;
ruolo_importatori.descrizione_ruolo:= input_ruolo_descrizione_importatori;
ruolo_importatori.end_point:= 'Importatori';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_importatori.end_point,ruolo_importatori.id_ruolo,ruolo_importatori.descrizione_ruolo,us_id,input_note);

ruolo_digemon.id_ruolo:= input_ruolo_id_digemon;
ruolo_digemon.descrizione_ruolo:= input_ruolo_descrizione_digemon;
ruolo_digemon.end_point:= 'Digemon';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_digemon.end_point,ruolo_digemon.id_ruolo,ruolo_digemon.descrizione_ruolo,us_id,input_note);

	
	
	RETURN msg||'_'||us_id;

END
$$;


ALTER FUNCTION public."OBSdbi_insert_utente_guc"(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text, input_luogo_vam text, input_id_provincia_vam integer, input_nr_iscrizione_vam text, input_gestore_acque integer, input_comune_gestore_acque integer) OWNER TO postgres;

--
-- Name: allinea_tutte_cliniche_vam_per_hd(integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.allinea_tutte_cliniche_vam_per_hd(_id_utente_guc integer, id_utente_operazione integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
DECLARE
   rec integer;
   conta_associazione integer;
   verifica_esistenza integer;
   query text;
   id_clinica_out integer;
   nome_clinica text;
   query_endpoint text;
   output_vam text;
   id_super_utente integer;
   id_nuovo_utente integer;
   categories_name text;
   codice_fiscale text;
   nome text;
   messaggio text;
BEGIN
	-- controllo preliminare su GUC per admin Vam
	verifica_esistenza := (select count(*) from guc_utenti u join guc_ruoli r on r.id_utente=u.id
	where (r.trashed is null or r.trashed is false) and u.enabled
	and r.ruolo_integer > 0 and r.endpoint ilike 'vam' 
	and r.ruolo_integer in (1,2,16,17) and u.id = _id_utente_guc);
	if (verifica_esistenza > 0) then 
	        raise info 'esiste in guc utente' ;
		codice_fiscale := (select g.codice_fiscale from guc_utenti g where g.id  = _id_utente_guc limit 1);
		id_super_utente :=(select id
				   --FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			FROM dblink('host=127.0.0.1 dbname=vam'::text, 'SELECT superutente from utenti where codice_fiscale ilike '''||codice_fiscale||''' limit 1') 
			t1(id integer));
		raise info 'id_super_utente %', id_super_utente;
		FOR rec IN
			select t1.id_clinica_out
				   --FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
			FROM dblink('host=127.0.0.1 dbname=vam'::text, 'SELECT * from public.dbi_get_cliniche_utente_attive()') 
			t1(asl_id integer, id_clinica_out integer, clinica_out text)
		LOOP
			raise info 'id clinica: %', rec;
			conta_associazione := (select count(*) from guc_cliniche_vam where id_clinica  = rec and id_utente = _id_utente_guc);
			if (conta_associazione = 0) then -- nessuna associazione quindi inserisci la clinica in guc
				--parte I GUC
				insert into guc_cliniche_vam(id_clinica, descrizione_clinica, id_utente) values(rec, (select clinica_out
																--FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
															FROM dblink('host=127.0.0.1 dbname=vam'::text, 'SELECT nome_clinica from public.dbi_get_cliniche_utente_attive() where id_clinica='||rec||'') 
															t1(clinica_out text)) 
				,_id_utente_guc);
				 -- parte 2 VAM
				 -- verifo se presente la clinica?
				 conta_associazione := (select t1.numero
							  FROM dblink('host=127.0.0.1 dbname=vam'::text, 'select count(*) from utenti where clinica  = '||rec||' and codice_fiscale ilike '''||codice_fiscale||'''') 
							  t1(numero integer));
				raise info 'numero associazione clinica %', conta_associazione;
				 if (conta_associazione = 0) then -- significa che in vam non esiste
					  raise info 'inserisco associazione in vam';
					  output_vam:= (select t1.esito
					  --FROM dblink('host=dbVAML dbname=vam'::text, 'SELECT 
								  FROM dblink('host=127.0.0.1 dbname=vam'::text, 'select * from public.allinea_tutte_cliniche_vam_per_hd('''||codice_fiscale||''',''Amministratore'','||rec||')')
								  t1(esito text)
							);
					raise info 'output vam: %',output_vam;
				end if; -- fine inserimento in vam
			end if;
			
		END LOOP;

		messaggio :='{"Esito" : "OK", "DescrizioneErrore" : ""}';

	else 
		messaggio :='{"Esito" : "KO", "DescrizioneErrore" : "Utente non presente in GU con ruolo di amministratore VAM"}';
	end if;
	
	return messaggio;
END;
$$;


ALTER FUNCTION public.allinea_tutte_cliniche_vam_per_hd(_id_utente_guc integer, id_utente_operazione integer) OWNER TO postgres;

--
-- Name: dbi_cambio_password(text, text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_cambio_password(input_username text, input_password text, input_password_old text, input_ip text) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
us_id integer;
md5_password text;
msg text;

BEGIN

md5_password = md5(input_password);

us_id := (select id from guc_utenti where username = input_username);

if (us_id>0) THEN

update guc_utenti set password = md5_password where id = us_id;
insert into storico_cambio_password (id_utente, ip_modifica, data_modifica, vecchia_password, nuova_password) values (us_id, input_ip, now(), input_password_old, md5_password);
msg = 'OK';

ELSE
msg ='KO';

	END IF;
	
	RETURN msg;

END
$$;


ALTER FUNCTION public.dbi_cambio_password(input_username text, input_password text, input_password_old text, input_ip text) OWNER TO postgres;

--
-- Name: dbi_cambio_password(text, text, text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_cambio_password(input_username text, input_password text, input_password_old text, input_ip text, input_password_decript text) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
us_id integer;
md5_password text;
msg text;

BEGIN

md5_password = md5(input_password);

us_id := (select id from guc_utenti where username = input_username);

if (us_id>0) THEN

update guc_utenti set password = md5_password where id = us_id;
insert into storico_cambio_password (id_utente, ip_modifica, data_modifica, vecchia_password, nuova_password, nuova_password_decript) values (us_id, input_ip, now(), input_password_old, md5_password, input_password_decript);
msg = 'OK';

ELSE
msg ='KO';

	END IF;
	
	RETURN msg;

END
$$;


ALTER FUNCTION public.dbi_cambio_password(input_username text, input_password text, input_password_old text, input_ip text, input_password_decript text) OWNER TO postgres;

--
-- Name: dbi_check_esistenza_cf_per_ruolo(text, integer, integer, integer, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_check_esistenza_cf_per_ruolo(input_cf text, input_id_ruolo_gisa integer, input_id_ruolo_bdu integer, input_id_ruolo_vam integer, input_id_ruolo_digemon integer, input_id_asl integer, input_id_canile_bdu integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
   
esistentiStessoCf integer;
msg text;

BEGIN

msg := '';

esistentiStessoCf := 0;
-- GISA
IF input_id_ruolo_gisa > 0 THEN
select count(u.id) into esistentiStessoCf
from guc_utenti u 
join guc_ruoli r on u.id = r.id_utente and r.endpoint='Gisa' and r.ruolo_integer>0
left join asl a on u.asl_id = a.id 
where u.enabled and u.codice_fiscale ilike input_cf and r.ruolo_integer = input_id_ruolo_gisa and u.asl_id = input_id_asl;
raise info '[CHECK ESISTENZA CF PER RUOLO] Controllo GISA: %', esistentiStessoCf;
IF esistentiStessoCf > 0 THEN
select concat(msg, 'ERRORE. A questo codice fiscale risultano associati utenti con lo stesso ruolo GISA e stessa ASL.') into msg;
END IF;
END IF;

esistentiStessoCf := 0;
-- BDU
IF input_id_ruolo_bdu > 0 THEN
select count(u.id) into esistentiStessoCf
from guc_utenti u 
join guc_ruoli r on u.id = r.id_utente and r.endpoint='bdu' and r.ruolo_integer>0
left join asl a on u.asl_id = a.id 
where u.enabled and u.codice_fiscale ilike input_cf and r.ruolo_integer = input_id_ruolo_bdu  and u.asl_id = input_id_asl and u.canilebdu_id = input_id_canile_bdu;
raise info '[CHECK ESISTENZA CF PER RUOLO] Controllo BDU: %', esistentiStessoCf;
IF esistentiStessoCf > 0 THEN
select concat(msg, 'ERRORE. A questo codice fiscale risultano associati utenti con lo stesso ruolo BDU e stessa ASL. (Vengono ignorati gli utenti CANILE se su canili diversi)') into msg;
END IF;
END IF;

esistentiStessoCf := 0;
-- VAM
IF input_id_ruolo_vam > 0 THEN
select count(u.id) into esistentiStessoCf
from guc_utenti u 
join guc_ruoli r on u.id = r.id_utente and r.endpoint='Vam' and r.ruolo_integer>0
left join asl a on u.asl_id = a.id 
where u.enabled and u.codice_fiscale ilike input_cf and r.ruolo_integer = input_id_ruolo_vam  and u.asl_id = input_id_asl;
raise info '[CHECK ESISTENZA CF PER RUOLO] Controllo VAM: %', esistentiStessoCf;
IF esistentiStessoCf > 0 THEN
select concat(msg, 'ERRORE. A questo codice fiscale risultano associati utenti con lo stesso ruolo VAM e stessa ASL.') into msg;
END IF;
END IF;

esistentiStessoCf := 0;
-- DIGEMON
IF input_id_ruolo_digemon > 0 THEN
select count(u.id) into esistentiStessoCf
from guc_utenti u 
join guc_ruoli r on u.id = r.id_utente and r.endpoint='Digemon' and r.ruolo_integer>0
left join asl a on u.asl_id = a.id 
where u.enabled and u.codice_fiscale ilike input_cf and r.ruolo_integer = input_id_ruolo_digemon  and u.asl_id = input_id_asl;
raise info '[CHECK ESISTENZA CF PER RUOLO] Controllo DIGEMON: %', esistentiStessoCf;
IF esistentiStessoCf > 0 THEN
select concat(msg, 'ERRORE. A questo codice fiscale risultano associati utenti con lo stesso ruolo DIGEMON e stessa ASL. ') into msg;
END IF;
END IF;

IF msg = '' THEN
msg = 'OK';
END IF;

return msg;
	
END
$$;


ALTER FUNCTION public.dbi_check_esistenza_cf_per_ruolo(input_cf text, input_id_ruolo_gisa integer, input_id_ruolo_bdu integer, input_id_ruolo_vam integer, input_id_ruolo_digemon integer, input_id_asl integer, input_id_canile_bdu integer) OWNER TO postgres;

--
-- Name: dbi_check_esistenza_utente(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_check_esistenza_utente(usr character varying) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
   DECLARE
esito boolean ;
tot int;   
BEGIN
	tot := (select count(*) from guc_utenti a where a.username = usr and enabled);

	IF (tot=0) THEN
		esito:=false;
	ELSE 	
		esito:=true;
	END IF;
	RETURN esito;
END
$$;


ALTER FUNCTION public.dbi_check_esistenza_utente(usr character varying) OWNER TO postgres;

--
-- Name: dbi_check_esistenza_utente_by_numreg(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_check_esistenza_utente_by_numreg(_numreg text) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
   DECLARE
esito boolean ;
tot int;   
BEGIN
	tot := (select count(*) from guc_utenti a where upper(num_registrazione_stab) = upper(_numreg) and enabled);

	IF (tot=0) THEN
		esito:=false;
	ELSE 	
		esito:=true;
	END IF;
	RETURN esito;
END
$$;


ALTER FUNCTION public.dbi_check_esistenza_utente_by_numreg(_numreg text) OWNER TO postgres;

--
-- Name: dbi_elimina_utente(text, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_elimina_utente(_username text, _data_scadenza timestamp without time zone) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
		errore='Non Ã¨ stato trovato nessun utente attivo con i dati indicati.';
	ELSE
	
		UPDATE utenti_ SET modified = now(), modified_by = 964, data_scadenza = _data_scadenza, note_internal_use_only_hd = concat_ws(';', note_internal_use_only_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente') WHERE id in (select a.id from utenti a 
	where a.username = _username and a.trashed_date is null and a.enabled and a.data_scadenza is null);
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$$;


ALTER FUNCTION public.dbi_elimina_utente(_username text, _data_scadenza timestamp without time zone) OWNER TO postgres;

--
-- Name: dbi_elimina_utente_ext(text, timestamp without time zone); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_elimina_utente_ext(_username text, _data_scadenza timestamp without time zone) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
		errore='Non Ã¨ stato trovato nessun utente attivo con i dati indicati.';
	ELSE
	
		UPDATE access_ext_ SET modified = now(), modifiedby = 964, data_scadenza = _data_scadenza, note_hd = concat_ws(';', note_hd, 'DATA SCADENZA VALORIZZATA TRAMITE dbi_elimina_utente_ext') WHERE user_id = idUtente;
		esito = 'OK';
	END IF;
	RETURN '{"Esito" : "'||esito||'", "DescrizioneErrore": "'||errore||'"}';
END
$$;


ALTER FUNCTION public.dbi_elimina_utente_ext(_username text, _data_scadenza timestamp without time zone) OWNER TO postgres;

--
-- Name: dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, integer, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_insert_utente(usr character varying, password character varying, role_id integer, enteredby integer, modifiedby integer, enabled boolean, site_id integer, namefirst character varying, namelast character varying, cf character varying, notes text, luogo text, nickname character varying, email character varying, expires timestamp with time zone, id_importatore integer, id_canile integer, id_prov_iscr_albo integer, nr_iscrizione_albo text, input_telefono text) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.dbi_insert_utente(usr character varying, password character varying, role_id integer, enteredby integer, modifiedby integer, enabled boolean, site_id integer, namefirst character varying, namelast character varying, cf character varying, notes text, luogo text, nickname character varying, email character varying, expires timestamp with time zone, id_importatore integer, id_canile integer, id_prov_iscr_albo integer, nr_iscrizione_albo text, input_telefono text) OWNER TO postgres;

--
-- Name: dbi_insert_utente_ext(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, text, text, text, integer, text, text, integer, text, text, integer, text, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_insert_utente_ext(usr character varying, password character varying, role_id integer, enteredby integer, modifiedby integer, enabled boolean, site_id integer, namefirst_input character varying, namelast_input character varying, cf character varying, notes text, luogo text, nickname character varying, email character varying, expires timestamp with time zone, inaccess text, inni text, numreg text, input_gestore_acque integer, input_piva text, input_tipo_attivita_apicoltore text, input_comune_apicoltore integer, input_indirizzo_apicoltore text, input_cap_indirizzo_apicoltore text, input_comune_trasportatore integer, input_indirizzo_trasportatore text, input_cap_indirizzo_trasportatore text, input_telefono text) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
msg text ;
us_id int ;
con_id int;
us_id2 int ;
flag boolean;
id_an int;
   
BEGIN

	namefirst_input:= trim(namefirst_input);
namelast_input:= trim(namelast_input);
cf:= trim(cf);

	IF (role_id=-1) THEN
		enabled:=false;
	ELSE
		enabled:=true;
	END IF;


con_id := (select contact_id from contact_ext_ c where c.namefirst ilike namefirst_input and c.namelast ilike namelast_input and c.codice_fiscale ilike cf and c.trashed_date is null limit 1);
	IF (con_id is null) THEN
			con_id=nextVal('contact_ext_contact_id_seq');

--con_id=currVal('contact_ext_contact_id_seq');
		--us_id=currVal('access_user_id_ext_seq');
		INSERT INTO contact_ext_ ( contact_id, namefirst, namelast, enteredby, modifiedby, codice_fiscale, notes, enabled,luogo,nickname, duns_number) 
		VALUES ( con_id, upper(namefirst_input),  upper(namelast_input), 964, 964, cf, notes, enabled,luogo,nickname, input_telefono);
			 
		--con_id=currVal('contact_ext_contact_id_seq');
		INSERT INTO contact_emailaddress_ext(contact_id, emailaddress_type, email, enteredby, modifiedby, primary_email)
		VALUES (con_id, 1, email, 964, 964, true);

		end if;
	

	
		us_id=nextVal('access_user_id_ext_seq');
		INSERT INTO access_ext_ ( user_id, username, password, contact_id, role_id, enteredby, modifiedby, timezone, currency, language, enabled, expires,in_access, in_nucleo_ispettivo) 
		VALUES (  us_id, usr, password, con_id, role_id, 964, 964, 'Europe/Berlin', 'EUR', 'it_IT', enabled, expires::timestamp without time zone, inaccess::boolean, inni::boolean); 

		INSERT INTO access_dati ( user_id, site_id, visibilita_delega, num_registrazione_stab, 
		tipo_attivita_apicoltore, comune_apicoltore ,indirizzo_apicoltore,cap_apicoltore,
		comune_trasportatore,indirizzo_trasportatore, cap_trasportatore) 
		VALUES (  us_id, site_id, (case when input_piva <> '' and role_id <> 10000008 then input_piva else cf end), numreg,
		input_tipo_attivita_apicoltore,input_comune_apicoltore ,input_indirizzo_apicoltore,input_cap_indirizzo_apicoltore,
		input_comune_trasportatore,input_indirizzo_trasportatore, input_cap_indirizzo_trasportatore); 

		if(input_gestore_acque>0) then
                   insert into users_to_gestori_acque(id_gestore_acque_anag, user_id) values(input_gestore_acque, us_id); 
		end if;


	
	msg = COALESCE(msg, 'OK');
	RETURN msg;

END
$$;


ALTER FUNCTION public.dbi_insert_utente_ext(usr character varying, password character varying, role_id integer, enteredby integer, modifiedby integer, enabled boolean, site_id integer, namefirst_input character varying, namelast_input character varying, cf character varying, notes text, luogo text, nickname character varying, email character varying, expires timestamp with time zone, inaccess text, inni text, numreg text, input_gestore_acque integer, input_piva text, input_tipo_attivita_apicoltore text, input_comune_apicoltore integer, input_indirizzo_apicoltore text, input_cap_indirizzo_apicoltore text, input_comune_trasportatore integer, input_indirizzo_trasportatore text, input_cap_indirizzo_trasportatore text, input_telefono text) OWNER TO postgres;

--
-- Name: dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text, integer, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_insert_utente_guc(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text, input_luogo_vam text, input_id_provincia_vam integer, input_nr_iscrizione_vam text, input_gestore_acque integer, input_comune_gestore_acque integer, input_piva text) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
msg text ;
us_id int ;
ruolo_gisa guc_ruolo;
ruolo_gisa_ext guc_ruolo;
ruolo_bdu guc_ruolo;
ruolo_vam guc_ruolo;
ruolo_importatori guc_ruolo;
ruolo_digemon guc_ruolo;

BEGIN


--Inserisco utente
us_id:= (select nextval('guc_utenti_id_seq'));
INSERT INTO guc_utenti_ (ID,CODICE_FISCALE,COGNOME,EMAIL,ENABLED,entered,enteredby,expires,modified,modifiedby,note,nome,password,username,asl_id,password_encrypted,canile_id, canile_description,luogo,num_autorizzazione,id_importatore,canilebdu_id, canilebdu_description,importatori_description,id_provincia_iscrizione_albo_vet_privato,nr_iscrione_albo_vet_privato,id_utente_guc_old,suap_ip_address ,suap_istat_comune ,suap_pec ,suap_callback ,suap_shared_key,suap_callback_ko,num_registrazione_stab,suap_livello_accreditamento,suap_descrizione_livello_accreditamento,telefono, luogo_vam, id_provincia_iscrizione_albo_vet_privato_vam, nr_iscrione_albo_vet_privato_vam, gestore_acque , comune_gestore_acque, piva ) 
values (us_id,input_cf,input_cognome,input_email,input_enabled,now(),input_enteredby,input_expires,now(),input_modifiedby,input_note, input_nome,input_password,input_username,input_asl_id,input_password_encrypted,input_canile_id, input_canile_description,input_luogo,input_num_autorizzazione,input_id_importatore,input_canilebdu_id, input_canilebdu_description,input_importatori_description,input_id_provincia_iscrizione_albo_vet_privato,input_nr_iscrione_albo_vet_privato,input_id_utente_guc_old,input_suap_ip_address ,input_suap_istat_comune ,input_suap_pec ,input_suap_callback ,input_suap_shared_key,input_suap_callback_ko,input_num_registrazione_stab,input_suap_livello_accreditamento,input_suap_descrizione_livello_accreditamento,input_telefono , input_luogo_vam, input_id_provincia_vam, input_nr_iscrizione_vam, input_gestore_acque, input_comune_gestore_acque, input_piva );

msg:='OK';


--Popolo ruoli e li inserisco

ruolo_gisa.id_ruolo:= input_ruolo_id_gisa;
ruolo_gisa.descrizione_ruolo:= input_ruolo_descrizione_gisa;
ruolo_gisa.end_point:= 'Gisa';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa.end_point,ruolo_gisa.id_ruolo,ruolo_gisa.descrizione_ruolo,us_id,input_note);

ruolo_gisa_ext.id_ruolo:= input_ruolo_id_gisa_ext;
ruolo_gisa_ext.descrizione_ruolo:= input_ruolo_descrizione_gisa_ext;
ruolo_gisa_ext.end_point:= 'Gisa_ext';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa_ext.end_point,ruolo_gisa_ext.id_ruolo,ruolo_gisa_ext.descrizione_ruolo,us_id,input_note);

ruolo_bdu.id_ruolo:= input_ruolo_id_bdu;
ruolo_bdu.descrizione_ruolo:= input_ruolo_descrizione_bdu;
ruolo_bdu.end_point:= 'bdu';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_bdu.end_point,ruolo_bdu.id_ruolo,ruolo_bdu.descrizione_ruolo,us_id,input_note);

ruolo_vam.id_ruolo:= input_ruolo_id_vam;
ruolo_vam.descrizione_ruolo:= input_ruolo_descrizione_vam;
ruolo_vam.end_point:= 'Vam';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_vam.end_point,ruolo_vam.id_ruolo,ruolo_vam.descrizione_ruolo,us_id,input_note);

ruolo_importatori.id_ruolo:= input_ruolo_id_importatori;
ruolo_importatori.descrizione_ruolo:= input_ruolo_descrizione_importatori;
ruolo_importatori.end_point:= 'Importatori';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_importatori.end_point,ruolo_importatori.id_ruolo,ruolo_importatori.descrizione_ruolo,us_id,input_note);

ruolo_digemon.id_ruolo:= input_ruolo_id_digemon;
ruolo_digemon.descrizione_ruolo:= input_ruolo_descrizione_digemon;
ruolo_digemon.end_point:= 'Digemon';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_digemon.end_point,ruolo_digemon.id_ruolo,ruolo_digemon.descrizione_ruolo,us_id,input_note);

	
	
	RETURN msg||'_'||us_id;

END
$$;


ALTER FUNCTION public.dbi_insert_utente_guc(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text, input_luogo_vam text, input_id_provincia_vam integer, input_nr_iscrizione_vam text, input_gestore_acque integer, input_comune_gestore_acque integer, input_piva text) OWNER TO postgres;

--
-- Name: dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text, integer, integer, text, text, integer, text, text, integer, text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_insert_utente_guc(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text, input_luogo_vam text, input_id_provincia_vam integer, input_nr_iscrizione_vam text, input_gestore_acque integer, input_comune_gestore_acque integer, input_piva text, input_tipo_attivita_apicoltore text, input_comune_apicoltore integer, input_indirizzo_apicoltore text, input_cap_indirizzo_apicoltore text, input_comune_trasportatore integer, input_indirizzo_trasportatore text, input_cap_indirizzo_trasportatore text) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
msg text ;
us_id int ;
input_password_decrypted text;
ruolo_gisa guc_ruolo;
ruolo_gisa_ext guc_ruolo;
ruolo_bdu guc_ruolo;
ruolo_vam guc_ruolo;
ruolo_importatori guc_ruolo;
ruolo_digemon guc_ruolo;

BEGIN


--Inserisco utente
us_id:= (select nextval('guc_utenti_id_seq'));

IF (input_username is null or input_username = '') THEN
select concat('spid_usr_',us_id) into input_username;
END IF;

IF (input_password  is null or input_password  = '') THEN
select concat('spid_pwd_',us_id) into input_password_decrypted;
select md5(input_password_decrypted) into input_password ;
END IF;

INSERT INTO guc_utenti_ (ID,CODICE_FISCALE,COGNOME,EMAIL,ENABLED,entered,enteredby,expires,modified,modifiedby,note,nome,password,username,asl_id,password_encrypted,canile_id, canile_description,luogo,num_autorizzazione,id_importatore,canilebdu_id, canilebdu_description,
importatori_description,id_provincia_iscrizione_albo_vet_privato,nr_iscrione_albo_vet_privato,id_utente_guc_old,suap_ip_address ,suap_istat_comune ,suap_pec ,
suap_callback ,suap_shared_key,suap_callback_ko,num_registrazione_stab,suap_livello_accreditamento,suap_descrizione_livello_accreditamento,telefono, 
luogo_vam, id_provincia_iscrizione_albo_vet_privato_vam, nr_iscrione_albo_vet_privato_vam, gestore_acque , comune_gestore_acque, piva, 
comune_apicoltore, comune_trasportatore, 
indirizzo_trasportatore, indirizzo_apicoltore, 
cap_trasportatore, cap_apicoltore, 
tipo_attivita_apicoltore) 
values (us_id,input_cf,input_cognome,input_email,input_enabled,now(),input_enteredby,input_expires,now(),input_modifiedby,input_note, input_nome,input_password,input_username,input_asl_id,input_password_encrypted,input_canile_id, 
input_canile_description,input_luogo,input_num_autorizzazione,input_id_importatore,input_canilebdu_id, input_canilebdu_description,input_importatori_description,
input_id_provincia_iscrizione_albo_vet_privato,input_nr_iscrione_albo_vet_privato,input_id_utente_guc_old,
input_suap_ip_address ,input_suap_istat_comune ,input_suap_pec ,input_suap_callback ,
input_suap_shared_key,input_suap_callback_ko,input_num_registrazione_stab,
input_suap_livello_accreditamento,input_suap_descrizione_livello_accreditamento,input_telefono , input_luogo_vam, 
input_id_provincia_vam, input_nr_iscrizione_vam, input_gestore_acque, input_comune_gestore_acque, input_piva, 
input_comune_apicoltore, input_comune_trasportatore, 
input_indirizzo_trasportatore, input_indirizzo_apicoltore, 
input_cap_indirizzo_trasportatore, input_cap_indirizzo_apicoltore, 
input_tipo_attivita_apicoltore);

msg:='OK';


--Popolo ruoli e li inserisco

ruolo_gisa.id_ruolo:= input_ruolo_id_gisa;
ruolo_gisa.descrizione_ruolo:= input_ruolo_descrizione_gisa;
ruolo_gisa.end_point:= 'Gisa';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa.end_point,ruolo_gisa.id_ruolo,ruolo_gisa.descrizione_ruolo,us_id,input_note);

ruolo_gisa_ext.id_ruolo:= input_ruolo_id_gisa_ext;
ruolo_gisa_ext.descrizione_ruolo:= input_ruolo_descrizione_gisa_ext;
ruolo_gisa_ext.end_point:= 'Gisa_ext';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa_ext.end_point,ruolo_gisa_ext.id_ruolo,ruolo_gisa_ext.descrizione_ruolo,us_id,input_note);

ruolo_bdu.id_ruolo:= input_ruolo_id_bdu;
ruolo_bdu.descrizione_ruolo:= input_ruolo_descrizione_bdu;
ruolo_bdu.end_point:= 'bdu';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_bdu.end_point,ruolo_bdu.id_ruolo,ruolo_bdu.descrizione_ruolo,us_id,input_note);

ruolo_vam.id_ruolo:= input_ruolo_id_vam;
ruolo_vam.descrizione_ruolo:= input_ruolo_descrizione_vam;
ruolo_vam.end_point:= 'Vam';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_vam.end_point,ruolo_vam.id_ruolo,ruolo_vam.descrizione_ruolo,us_id,input_note);

ruolo_importatori.id_ruolo:= input_ruolo_id_importatori;
ruolo_importatori.descrizione_ruolo:= input_ruolo_descrizione_importatori;
ruolo_importatori.end_point:= 'Importatori';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_importatori.end_point,ruolo_importatori.id_ruolo,ruolo_importatori.descrizione_ruolo,us_id,input_note);

ruolo_digemon.id_ruolo:= input_ruolo_id_digemon;
ruolo_digemon.descrizione_ruolo:= input_ruolo_descrizione_digemon;
ruolo_digemon.end_point:= 'Digemon';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_digemon.end_point,ruolo_digemon.id_ruolo,ruolo_digemon.descrizione_ruolo,us_id,input_note);
	RETURN concat(msg,';;',us_id,';;','spid_usr_',us_id,';;',input_password_decrypted);
END
$$;


ALTER FUNCTION public.dbi_insert_utente_guc(input_cf text, input_cognome text, input_email text, input_enabled boolean, input_enteredby integer, input_expires timestamp without time zone, input_modifiedby integer, input_note text, input_nome text, input_password text, input_username text, input_asl_id integer, input_password_encrypted text, input_canile_id integer, input_canile_description text, input_luogo text, input_num_autorizzazione text, input_id_importatore integer, input_canilebdu_id integer, input_canilebdu_description text, input_importatori_description text, input_id_provincia_iscrizione_albo_vet_privato integer, input_nr_iscrione_albo_vet_privato text, input_id_utente_guc_old integer, input_suap_ip_address text, input_suap_istat_comune text, input_suap_pec text, input_suap_callback text, input_suap_shared_key text, input_suap_callback_ko text, input_num_registrazione_stab text, input_suap_livello_accreditamento integer, input_suap_descrizione_livello_accreditamento text, input_telefono text, input_ruolo_id_gisa integer, input_ruolo_descrizione_gisa text, input_ruolo_id_gisa_ext integer, input_ruolo_descrizione_gisa_ext text, input_ruolo_id_bdu integer, input_ruolo_descrizione_bdu text, input_ruolo_id_vam integer, input_ruolo_descrizione_vam text, input_ruolo_id_importatori integer, input_ruolo_descrizione_importatori text, input_ruolo_id_digemon integer, input_ruolo_descrizione_digemon text, input_luogo_vam text, input_id_provincia_vam integer, input_nr_iscrizione_vam text, input_gestore_acque integer, input_comune_gestore_acque integer, input_piva text, input_tipo_attivita_apicoltore text, input_comune_apicoltore integer, input_indirizzo_apicoltore text, input_cap_indirizzo_apicoltore text, input_comune_trasportatore integer, input_indirizzo_trasportatore text, input_cap_indirizzo_trasportatore text) OWNER TO postgres;

--
-- Name: dbi_update_cf_utente_guc(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_update_cf_utente_guc(input_username text, input_cf text) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
msg text ;
us_id integer;
role_id_ integer;
cf_old text;
BEGIN

--Controllo se l'username esiste già
us_id := (select id from guc_utenti where username = input_username);
cf_old := (select codice_fiscale from guc_utenti where username = input_username);
role_id_ := (select ruolo_integer from guc_ruoli where endpoint = 'Gisa_ext' and  id_utente = us_id);

if (us_id>0) THEN
	--questa condizione deve essere abilitata se si vuole implementare l'opzione che permette di fare l'update a tutti gli utenti che condividono il codice fiscale
	--in questa condizione veniva fatto in modo che l'opzione si attivasse solo se il ruolo era giava
	--if (role_id_ != 10000008) THEN
	if (true) THEN
		UPDATE guc_utenti_ set codice_fiscale = input_cf where id = us_id;
	ELSE
		UPDATE guc_utenti_ set codice_fiscale = input_cf where codice_fiscale = cf_old and id in (select id_utente from guc_ruoli where ruolo_integer =  10000008);
	END IF;
	msg:='OK';
ELSE
	msg:= 'Errore. Username non esistente.';
END IF;
	
RETURN msg;

END
$$;


ALTER FUNCTION public.dbi_update_cf_utente_guc(input_username text, input_cf text) OWNER TO postgres;

--
-- Name: dbi_update_email_utente_guc(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_update_email_utente_guc(input_username text, input_email text) RETURNS text
    LANGUAGE plpgsql
    AS $$
   DECLARE
msg text ;
us_id integer;
role_id_ integer;
email_old text;
BEGIN

--Controllo se l'username esiste già
us_id := (select id from guc_utenti where username = input_username);
email_old := (select email from guc_utenti where username = input_username);
role_id_ := (select ruolo_integer from guc_ruoli where endpoint = 'Gisa_ext' and  id_utente = us_id);

if (us_id>0) THEN
	--questa condizione deve essere abilitata se si vuole implementare l'opzione che permette di fare l'update a tutti gli utenti che condividono il codice fiscale
	--in questa condizione veniva fatto in modo che l'opzione si attivasse solo se il ruolo era giava
	--if (role_id_ != 10000008) THEN
	
	UPDATE guc_utenti_ set email = input_email where id = us_id;
	
	msg:='OK';
ELSE
	msg:= 'Errore. Username non esistente.';
END IF;
	
RETURN msg;

END
$$;


ALTER FUNCTION public.dbi_update_email_utente_guc(input_username text, input_email text) OWNER TO postgres;

--
-- Name: dbi_verifica_cambio_password_recente(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_verifica_cambio_password_recente(input_username text) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
   DECLARE
esito boolean;
us_id integer;
data timestamp without time zone;
diff_secondi double precision;
BEGIN

esito:=false;

us_id := (select id from guc_utenti where username ilike input_username);

if (us_id>0) THEN

data := (select max(data_modifica) from storico_cambio_password where id_utente = us_id);

IF data is not null THEN
	diff_secondi := (SELECT EXTRACT(EPOCH FROM (now() - data)));
	IF (diff_secondi < 240) THEN
	esito:= true;
	END IF;
	END IF;

END IF;
	
	RETURN esito;

END
$$;


ALTER FUNCTION public.dbi_verifica_cambio_password_recente(input_username text) OWNER TO postgres;

--
-- Name: dbi_verifica_password_precedente(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_verifica_password_precedente(input_username text, input_password text) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
   DECLARE
esito boolean;
us_id integer;

BEGIN

us_id := (select id from guc_utenti where username ilike input_username and password = md5(input_password));

if (us_id>0) THEN

esito:=true;

ELSE

esito:=false;

	END IF;
	
	RETURN esito;

END
$$;


ALTER FUNCTION public.dbi_verifica_password_precedente(input_username text, input_password text) OWNER TO postgres;

--
-- Name: dbi_verifica_ultima_modifica_password(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.dbi_verifica_ultima_modifica_password(input_username text) RETURNS timestamp without time zone
    LANGUAGE plpgsql
    AS $$
   DECLARE
us_id integer;
data_modifica_password timestamp without time zone;


BEGIN

data_modifica_password := (select max(s.data_modifica) from storico_cambio_password s join guc_utenti_ u on u.id = s.id_utente where u.username = input_username);

if (data_modifica_password is null) THEN
data_modifica_password := (select max(entered) from guc_utenti_ where username = input_username);
END IF;
	
	RETURN data_modifica_password;

END
$$;


ALTER FUNCTION public.dbi_verifica_ultima_modifica_password(input_username text) OWNER TO postgres;

--
-- Name: disattiva_utente(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.disattiva_utente(_id_utente integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
 DECLARE 
	
	conta integer;	
	esito text;
	descrizione_errore text;
  BEGIN

	descrizione_errore ='';
	esito='';
	
	conta := (select count(*) from 
		  guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.id = _id_utente and (r.data_scadenza is null and (r.trashed is null or r.trashed =false))
		  and r.ruolo_integer > 0
		  );
	
	if(conta > 0) then
		raise info 'Utente attivo nel sistema.';
		esito = 'KO';
		descrizione_errore = 'Non si puo'' procedere alla disattivazione dell''utente.';
	else 
		raise info 'Utente da disattivare';
		update guc_utenti_ set note_internal_use_only_hd=concat_ws('***',note_internal_use_only_hd,'Disattivazione automatica utente tramite funzione public.disattiva_utente('||_id_utente||')'), data_scadenza= current_timestamp, enabled=false where id = _id_utente;
		
		esito = 'OK';
		descrizione_errore = '';
	end if;
	 			   
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'"}';
	
END
$$;


ALTER FUNCTION public.disattiva_utente(_id_utente integer) OWNER TO postgres;

--
-- Name: get_json_valore(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_json_valore(_jsontext text, _chiave text) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION public.get_json_valore(_jsontext text, _chiave text) OWNER TO postgres;

--
-- Name: get_storico_cambio_password(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.get_storico_cambio_password() RETURNS TABLE(username text, data_modifica timestamp without time zone, vecchia_password text, nuova_password text, nuova_password_decript text, ip_modifica text, decripta_old text, decripta_new text)
    LANGUAGE plpgsql STRICT
    AS $$
DECLARE
r RECORD;	
BEGIN
FOR username, data_modifica, vecchia_password, nuova_password, nuova_password_decript, ip_modifica, decripta_old, decripta_new
in
select u.username, s.data_modifica, s.vecchia_password, s.nuova_password, s.nuova_password_decript, s.ip_modifica,
'http://sca.gisacampania.it/sca/cambiopassword.DecriptaPassword.us?pwdDaDecriptare='||s.vecchia_password as decripta_old,
'http://sca.gisacampania.it/sca/cambiopassword.DecriptaPassword.us?pwdDaDecriptare='||s.nuova_password_decript as decripta_new

  from storico_cambio_password s
left join guc_utenti_ u on u.id = s.id_utente
order by s.data_modifica desc

    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
 END;
$$;


ALTER FUNCTION public.get_storico_cambio_password() OWNER TO postgres;

--
-- Name: is_administrator(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.is_administrator(input_username text, input_password text) RETURNS boolean
    LANGUAGE plpgsql
    AS $$
   DECLARE
output boolean;
role_id integer;
BEGIN

output := false;

select r.id into role_id from utenti u
join permessi_ruoli r on u.ruolo = r.nome
where u.username = input_username and u.password = md5(input_password) and u.enabled;

IF role_id = 1 OR role_id = 2 THEN
output := true;
END IF;

return output;
END
$$;


ALTER FUNCTION public.is_administrator(input_username text, input_password text) OWNER TO postgres;

--
-- Name: lista_utenti(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.lista_utenti(_endpoint text DEFAULT ''::text) RETURNS TABLE(codice_fiscale text, username text, id_asl integer, descrizione_asl text, id_utente integer, sistema text, id_ruolo integer, descrizione_ruolo text)
    LANGUAGE plpgsql STRICT
    AS $$
DECLARE
BEGIN

	return query 
	select upper(a.codice_fiscale)::text as codice_fiscale, a.username::text as username, a.asl_id, (select nome::text from asl where id = a.asl_id),  r.id_utente, r.endpoint::text as endpoint, r.ruolo_integer, r.ruolo_string::text as ruolo
	from (
	select g.codice_fiscale, g.id, g.username, g.asl_id  from guc_utenti g where id in (
	(select l.id_utente from guc_ruoli l where  (l.trashed is null or l.trashed is false) and l.endpoint ilike '%bdu%' and l.ruolo_integer > 0 and l.ruolo_integer in (20,29,34,36,18)
	except
	select m.id_utente from guc_ruoli m where (m.trashed is null or m.trashed is false) and m.endpoint ilike '%vam%' and m.ruolo_integer > 0 and m.ruolo_integer in(16,1,2,17,3,5,14) )
	UNION
	-- query per tirare fuori utenti che hanno ruolo VAM e non BDU
	(
	select n.id_utente from guc_ruoli n where (n.trashed is null or n.trashed is false) and n.endpoint ilike '%vam%' and n.ruolo_integer > 0 and n.ruolo_integer in(16,1,2,17,3,5,14)
	except 
	select o.id_utente from guc_ruoli o where (o.trashed is null or o.trashed is false) and o.endpoint ilike '%bdu%' and o.ruolo_integer > 0 and o.ruolo_integer in (20,29,34,36,18)
	))
	and data_scadenza is null and enabled order by id desc
	) a join guc_ruoli r on r.id_utente= a.id 
	where (r.trashed is null or r.trashed is false) 
	and (r.endpoint ilike '%vam%' and (r.ruolo_integer > 0 and r.ruolo_integer in(16,1,2,17,3,5,14)) or (r.endpoint ilike '%bdu%' and r.ruolo_integer > 0 and r.ruolo_integer in (20,29,34,36,18)))
	and (_endpoint = '' or r.endpoint ilike _endpoint)
	order by a.codice_fiscale;

 END;
$$;


ALTER FUNCTION public.lista_utenti(_endpoint text) OWNER TO postgres;

--
-- Name: raggruppa_utente_vam_bdu_asl(integer, integer, integer, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.raggruppa_utente_vam_bdu_asl(_id_guc_utente_1 integer, _id_ruolo_guc_bdu integer, _id_guc_utente_2 integer, _id_ruolo_guc_vam integer, username_rif text) RETURNS text
    LANGUAGE plpgsql
    AS $$

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
$$;


ALTER FUNCTION public.raggruppa_utente_vam_bdu_asl(_id_guc_utente_1 integer, _id_ruolo_guc_bdu integer, _id_guc_utente_2 integer, _id_ruolo_guc_vam integer, username_rif text) OWNER TO postgres;

--
-- Name: aggiorna_flag(text, boolean, boolean); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.aggiorna_flag(_numero_richiesta text, _in_nucleo boolean, _in_dpat boolean) RETURNS text
    LANGUAGE plpgsql
    AS $$
declare
 _id integer;
BEGIN
update spid.spid_registrazioni_flag set trashed_date = now() where numero_richiesta = _numero_richiesta;
insert into spid.spid_registrazioni_flag (numero_richiesta, in_nucleo, in_dpat) values (_numero_richiesta, _in_nucleo, _in_dpat);
return 'OK';
END
$$;


ALTER FUNCTION spid.aggiorna_flag(_numero_richiesta text, _in_nucleo boolean, _in_dpat boolean) OWNER TO postgres;

--
-- Name: check_validita_gruppo_ruolo_cf(text, integer, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.check_validita_gruppo_ruolo_cf(_codice_fiscale text, _id_ruolo integer, _endpoint text) RETURNS text
    LANGUAGE plpgsql
    AS $$
 DECLARE 
	_idutente_presente integer;
	_idruolo_presente integer;
	_username_presente text;
	_password_presente text;
	conta integer;	
	out text;
	esito text;
	descrizione_errore text;
  BEGIN

	descrizione_errore ='';
	esito='';
	_idutente_presente = -1;
	_idruolo_presente = -1;
	_username_presente = '';
	_password_presente = '';
	
	conta := (select count(*) from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
join spid.spid_tipologia_utente_ruoli_endpoint sture on sture.id_ruolo = r.ruolo_integer and sture.endpoint ilike r.endpoint
join spid.spid_tipologia_utente_ruoli_endpoint sture2 on sture.id_tipologia_utente = sture2.id_tipologia_utente and sture.endpoint ilike sture2.endpoint
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.codice_fiscale = _codice_fiscale and r.data_scadenza is null
		  and r.endpoint ilike _endpoint and sture2.id_ruolo = _id_ruolo);
	
	if(conta > 1) then
		raise info '[check_validita_gruppo_ruolo_cf] ERRORE. TROVATI PIU'' UTENTI PER IL GRUPPO DI QUESTA COPPIA RUOLO/ENDPOINT';
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare univocamente utente soggetto a modifica. Risultano esistenti diversi utenti per la coppia codice fiscale/ruolo.';
	elsif (conta = 0) then
		raise info '[check_validita_gruppo_ruolo_cf] ERRORE. NON TROVATO NESSUN UTENTE PER IL GRUPPO DI QUESTA COPPIA RUOLO/ENDPOINT';
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare utente soggetto a modifica. Non risulta esistente nessun utente per coppia codice fiscale/ruolo.';
	else 
		raise info '[check_validita_gruppo_ruolo_cf] Trovato utente!';
		select u.id, u.username, u.password, r.ruolo_integer  into _idutente_presente, _username_presente, _password_presente, _idruolo_presente from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
join spid.spid_tipologia_utente_ruoli_endpoint sture on sture.id_ruolo = r.ruolo_integer and sture.endpoint ilike r.endpoint
join spid.spid_tipologia_utente_ruoli_endpoint sture2 on sture.id_tipologia_utente = sture2.id_tipologia_utente and sture.endpoint ilike sture2.endpoint
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and 
		  u.codice_fiscale = _codice_fiscale and r.data_scadenza is null
		  and r.endpoint ilike _endpoint and sture2.id_ruolo = _id_ruolo;

		raise info 'id utente GUC: %', _idutente_presente;
		
		esito = 'OK';
	end if;
	 
				   
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'", "IdRuolo": "'||_idruolo_presente||'", "IdUtente": "'||_idutente_presente||'", "Username": "'||_username_presente||'" , "Password": "'||_password_presente||'"}';

	
END
$$;


ALTER FUNCTION spid.check_validita_gruppo_ruolo_cf(_codice_fiscale text, _id_ruolo integer, _endpoint text) OWNER TO postgres;

--
-- Name: check_validita_ruolo_cf(text, integer, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.check_validita_ruolo_cf(_codice_fiscale text, _id_ruolo integer, _endpoint text) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION spid.check_validita_ruolo_cf(_codice_fiscale text, _id_ruolo integer, _endpoint text) OWNER TO postgres;

--
-- Name: check_vincoli_ruoli_by_endpoint(text, integer, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.check_vincoli_ruoli_by_endpoint(_numero_richiesta text, _id_ruolo integer, _endpoint text) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
$$;


ALTER FUNCTION spid.check_vincoli_ruoli_by_endpoint(_numero_richiesta text, _id_ruolo integer, _endpoint text) OWNER TO postgres;

--
-- Name: elimina_ruolo_utente_guc(integer, integer, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.elimina_ruolo_utente_guc(_id_utente integer, _id_ruolo integer, _endpoint text) RETURNS text
    LANGUAGE plpgsql
    AS $$

  DECLARE 
	contaScadutiStessoGiorno integer;
	conta integer;	
	esito text;
	descrizione_errore text;
  BEGIN
	descrizione_errore='';
	esito='';

	contaScadutiStessoGiorno := (  select count(*)  from guc_ruoli where id_utente = _id_utente and endpoint = _endpoint
and date(data_scadenza) =  date(now() - interval '1 DAY'));

RAISE INFO '[elimina_ruolo_utente_guc] contaScadutiStessoGiorno %', contaScadutiStessoGiorno;
 
	IF contaScadutiStessoGiorno > 0 THEN
		esito = 'KO';
		descrizione_errore = 'Per questo codice fiscale risulta gia'' presente una richiesta di modifica o cancellazione nelle 24 ore precedenti. Impossibile proseguire.';

	ELSE
	
	conta := (select count(*) from guc_utenti_ u 
		  join guc_ruoli r on u.id = r.id_utente
		  where u.enabled and u.cessato <> true and u.data_scadenza is null and r.trashed is null and
		  u.id = _id_utente
		  and r.endpoint ilike _endpoint and r.ruolo_integer = _id_ruolo);
	
	if(conta > 1) then
		esito = 'KO';
		descrizione_errore = 'Impossibile identificare l''utente per questo codice fiscale e ruolo';
	elsif (conta = 0) then
		esito = 'KO';
		descrizione_errore = 'Non esiste alcun utente con questo codice fiscale e ruolo.';
	else 
		raise info 'Trovato 1 utente in GUC..';
		update guc_ruoli set trashed=true, data_scadenza = (now() - interval '1 DAY'), note=concat_ws('***',note,'Richiesta di disattivazione account') where 
		id_utente = _id_utente and ruolo_integer = _id_ruolo and endpoint ilike _endpoint;
		
		esito = 'OK';
	end if;
	END IF;
	
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'"}';

  END;
$$;


ALTER FUNCTION spid.elimina_ruolo_utente_guc(_id_utente integer, _id_ruolo integer, _endpoint text) OWNER TO postgres;

--
-- Name: esegui_query(text, text, integer, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.esegui_query(_query text, _db text, _id_utente integer, _host text) RETURNS text
    LANGUAGE plpgsql
    AS $$
declare
_output text;
idLog integer;
 
BEGIN

_output := '';

RAISE INFO '[esegui_query] _query-> %', _query;
RAISE INFO '[esegui_query] _db-> %', _db;
RAISE INFO '[esegui_query] _host-> %', _host;

insert into spid.log_query_generate (host, db, query, enteredby) values(_host, _db, _query, _id_utente) returning id into idLog;

IF _host <> '' THEN

_output  := (select t1.output FROM dblink(_host::text, _query) as t1(output text));

ELSE

EXECUTE FORMAT(_query) INTO _output;

END IF;

RAISE INFO '[esegui_query] _output-> %', _output;
          
update spid.log_query_generate set output = _output where id = idLog;

return _output;
END;
$$;


ALTER FUNCTION spid.esegui_query(_query text, _db text, _id_utente integer, _host text) OWNER TO postgres;

--
-- Name: get_dbi_per_endpoint(text, text, text, integer, text, text, timestamp without time zone, integer); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.get_dbi_per_endpoint(_numero_richiesta text, _endpoint text, _tipologia text, _id_utente integer, _username text, _password text, _data_scadenza timestamp without time zone, _id_ruolo_old integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
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

IF _tipologia ilike 'INSERT' THEN

IF _endpoint ilike 'GUC' then--30

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
'''', (select codice_gisa from spid.get_lista_richieste(_numero_richiesta)), '''', ', ', --31simo parametro numero stab
--'''', '', '''', ', ',
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
'''', (select coalesce(piva_numegistrazione,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
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
'''', (select codice_gisa from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
--'''', '', '''', ', ',numreg 18simo param
-1, ', ',
'''', (select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
'''', '', '''', ', ',
-1, ', ', 
'''', (select replace(coalesce(indirizzo,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
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
(select coalesce(id_clinica_vam,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ',
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
$$;


ALTER FUNCTION spid.get_dbi_per_endpoint(_numero_richiesta text, _endpoint text, _tipologia text, _id_utente integer, _username text, _password text, _data_scadenza timestamp without time zone, _id_ruolo_old integer) OWNER TO postgres;

--
-- Name: get_dbi_per_endpoint(text, text, text, integer, text, text, timestamp without time zone, integer, integer); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.get_dbi_per_endpoint(_numero_richiesta text, _endpoint text, _tipologia text, _id_utente integer, _username text, _password text, _data_scadenza timestamp without time zone, _id_ruolo_old integer, _indice integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
'''', (select codice_gisa from spid.get_lista_richieste(_numero_richiesta)), '''', ', ', --31simo parametro numero stab
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
'''', (select coalesce(piva_numregistrazione,'') from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
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
'''', (select codice_gisa from spid.get_lista_richieste(_numero_richiesta)), '''', ', ',
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
$$;


ALTER FUNCTION spid.get_dbi_per_endpoint(_numero_richiesta text, _endpoint text, _tipologia text, _id_utente integer, _username text, _password text, _data_scadenza timestamp without time zone, _id_ruolo_old integer, _indice integer) OWNER TO postgres;

--
-- Name: get_dettagli_estesi_utente_guc(integer); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.get_dettagli_estesi_utente_guc(_idutente integer) RETURNS json
    LANGUAGE plpgsql
    AS $$
   DECLARE
_username text;
_output json;
BEGIN

SELECT username into _username from guc_utenti_ where id = _idutente;

select row_to_json (row) into _output from 
(select 

u.num_registrazione_stab as "Numero Registrazione GISA",
gac.nome as "Gestore Acque",
trim(u.piva) as "Partita Iva",
(select id_tipologia_utente from spid.spid_registrazioni r
join spid.spid_registrazioni_esiti e on r.numero_richiesta = e.numero_richiesta
where get_json_valore(e.json_esito, 'Username') = _username and e.trashed_date is null 
--where e.json_esito ilike '%'||_username||'%' and e.trashed_date is null 
order by data_esito desc limit 1) as "IdTipologiaUtente"
from guc_utenti_ u

left join (select * FROM dblink('host=dbGISAL dbname=gisa'::text, 'SELECT id, nome::text from public_functions.dbi_get_gestori_acque(-1)')  t1(id integer, nome text)) gac on gac.id = u.gestore_acque

where u.id = _idutente
) row;

return _output;

END
$$;


ALTER FUNCTION spid.get_dettagli_estesi_utente_guc(_idutente integer) OWNER TO postgres;

--
-- Name: get_lista_richieste(text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.get_lista_richieste(_numero_richiesta text DEFAULT NULL::text) RETURNS TABLE(id integer, id_tipologia_utente integer, tipologia_utente text, id_tipo_richiesta integer, tipo_richiesta text, cognome text, nome text, codice_fiscale text, email text, telefono text, id_ruolo_gisa integer, ruolo_gisa text, id_ruolo_bdu integer, ruolo_bdu text, id_ruolo_vam integer, ruolo_vam text, id_ruolo_gisa_ext integer, ruolo_gisa_ext text, id_ruolo_digemon integer, ruolo_digemon text, id_clinica_vam integer[], clinica_vam text[], identificativo_ente text, piva_numregistrazione text, comune text, istat_comune text, nominativo_referente text, ruolo_referente text, email_referente text, telefono_referente text, data_richiesta timestamp without time zone, codice_gisa text, indirizzo text, id_gestore_acque integer, gestore_acque text, cap text, pec text, numero_richiesta text, esito_guc boolean, esito_gisa boolean, esito_gisa_ext boolean, esito_bdu boolean, esito_vam boolean, esito_digemon boolean, data_esito timestamp without time zone, stato integer, id_asl integer, asl text, provincia_ordine_vet text, numero_ordine_vet text, numero_decreto_prefettizio text, scadenza_decreto_prefettizio text, numero_autorizzazione_regionale_vet text, id_tipologia_trasp_dist integer, esito text, id_guc_ruoli integer, endpoint_guc_ruoli text, ruolo_guc_ruoli text, in_nucleo boolean, in_dpat boolean)
    LANGUAGE plpgsql
    AS $$
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
			id, nome::text from public_functions.dbi_get_gestori_acque(-1)')  -----> perchÃ¯Â¿Â½ non va bene passare il valore???
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
$$;


ALTER FUNCTION spid.get_lista_richieste(_numero_richiesta text) OWNER TO postgres;

--
-- Name: get_lista_ruoli_utente_guc(text, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.get_lista_ruoli_utente_guc(_cf text, _endpoint text) RETURNS TABLE(id_utente integer, username text, id_ruolo integer, id_guc_ruoli integer, ruolo text, id_asl integer, asl text, endpoint text, tipologia_utente text, dettagli json)
    LANGUAGE plpgsql
    AS $$
DECLARE
BEGIN
RETURN QUERY
 
select
u.id, u.username::text, r.ruolo_integer, r.id, r.ruolo_string::text, u.asl_id, a.nome::text, r.endpoint::text, string_agg(sture.id_tipologia_utente::text,','), (select * from spid.get_dettagli_estesi_utente_guc(u.id))
from guc_utenti u
left join guc_ruoli r on u.id = r.id_utente and r.trashed is not true and r.data_scadenza is null and r.ruolo_integer > 0
left join spid.spid_tipologia_utente_ruoli_endpoint sture on sture.id_ruolo = r.ruolo_integer and sture.endpoint ilike _endpoint and sture.enabled
left join asl a on a.id = u.asl_id
where u.codice_fiscale ilike _cf and r.endpoint ilike _endpoint
group by u.id, u.username::text, r.ruolo_integer, r.id, r.ruolo_string::text, u.asl_id, a.nome::text, r.endpoint::text;
 END; 
$$;


ALTER FUNCTION spid.get_lista_ruoli_utente_guc(_cf text, _endpoint text) OWNER TO postgres;

--
-- Name: insert_guc_clinica(integer, integer, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.insert_guc_clinica(_id_utente integer, _id_clinica integer, _descrizione_clinica text) RETURNS integer
    LANGUAGE plpgsql
    AS $$

  DECLARE ret_id integer;
 
  BEGIN
   
    INSERT INTO guc_cliniche_vam (id_utente, id_clinica, descrizione_clinica) VALUES (_id_utente, _id_clinica, _descrizione_clinica)		
    RETURNING id into ret_id;
  
  RETURN ret_id;
  END;
$$;


ALTER FUNCTION spid.insert_guc_clinica(_id_utente integer, _id_clinica integer, _descrizione_clinica text) OWNER TO postgres;

--
-- Name: insert_ruolo_utente_guc(integer, integer, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.insert_ruolo_utente_guc(_id_utente integer, _id_ruolo integer, _endpoint text) RETURNS text
    LANGUAGE plpgsql
    AS $$

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
		descrizione_errore = 'Esiste giÃ  un utente per questo codice fiscale e ruolo';
	else
	insert into guc_ruoli (id_utente, ruolo_integer, ruolo_string, endpoint) values (_id_utente, _id_ruolo, descrizione_ruolo, (select endpoint from guc_ruoli where endpoint ilike _endpoint limit 1));
		esito = 'OK';
	end if;
	
	return '{"Esito" : "'||esito||'", "DescrizioneErrore" : "'||descrizione_errore ||'"}';

  END;
$$;


ALTER FUNCTION spid.insert_ruolo_utente_guc(_id_utente integer, _id_ruolo integer, _endpoint text) OWNER TO postgres;

--
-- Name: insert_sca_storico_login(text, text, boolean, text); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.insert_sca_storico_login(_username text, _codice_fiscale text, _accesso_spid boolean, _endpoint text) RETURNS integer
    LANGUAGE plpgsql
    AS $$
declare
 _id integer;
BEGIN

	insert into spid.sca_storico_login(username, endpoint, codice_fiscale,accesso_spid) values (_username, _endpoint, _codice_fiscale, _accesso_spid) returning id into _id;
	return _id;
END
$$;


ALTER FUNCTION spid.insert_sca_storico_login(_username text, _codice_fiscale text, _accesso_spid boolean, _endpoint text) OWNER TO postgres;

--
-- Name: processa_richiesta(text, integer); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.processa_richiesta(_numero_richiesta text, _id_utente integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
	end if;
	
	return output;
END;
$$;


ALTER FUNCTION spid.processa_richiesta(_numero_richiesta text, _id_utente integer) OWNER TO postgres;

--
-- Name: processa_richiesta_elimina(text, integer); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.processa_richiesta_elimina(_numero_richiesta text, _id_utente integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
declare
	esito integer;

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

	output_disattiva text;
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
			output_gisa := COALESCE(output_guc, '');

			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa', 'elimina', _id_utente, username_out, '', now()::timestamp without time zone - interval '1 DAY',_id_ruolo_guc_ruoli, -1));
				output_gisa := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		  end if; -- fine ep_gisa

	if (_endpoint_guc_ruoli ilike 'gisa_ext') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa_ext'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_gisa_ext := COALESCE(output_guc, '');

			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa_ext', _id_utente,'host=dbGISAL dbname=gisa'));
			end if;	
		end if; -- fine ep_gisa_ext
        
	if (_endpoint_guc_ruoli ilike 'bdu') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''bdu'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_bdu := COALESCE(output_guc, '');
			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'bdu', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_bdu := (select * from spid.esegui_query(query_endpoint, 'bdu', _id_utente,'host=dbBDUL dbname=bdu'));
				
			end if;	
		end if; -- fine ep_bdu
        
	if (_endpoint_guc_ruoli ilike 'vam') then
		
		-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''vam'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_vam := COALESCE(output_guc, '');

			if (get_json_valore(output_guc, 'Esito') = 'OK') then
				query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'vam', 'elimina', _id_utente, username_out, '', (now()::timestamp without time zone - interval '1 DAY'),_id_ruolo_guc_ruoli, -1));
				output_vam := (select * from spid.esegui_query(query_endpoint, 'vam', _id_utente,'host=dbVAML dbname=vam'));
			end if;	
		  end if; -- fine ep_vam

	if (_endpoint_guc_ruoli ilike 'digemon') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''digemon'')';
			output_guc := (select * from spid.esegui_query(query , 'guc', _id_utente,''));
			output_digemon := COALESCE(output_guc, '');

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

	IF get_json_valore(output_gisa, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_gisa_ext, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_bdu, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_vam, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_digemon, 'Esito')='OK' THEN
		esito = 1;
	END IF;

	output_disattiva := (select * from public.disattiva_utente(_id_utente_guc_ruoli));

	UPDATE spid.spid_registrazioni_esiti set trashed_date = now() where numero_richiesta = _numero_richiesta and trashed_date is null;
	insert into spid.spid_registrazioni_esiti(numero_richiesta, esito_guc, esito_gisa, esito_gisa_ext, esito_bdu, esito_vam, esito_digemon,id_utente_esito, stato, json_esito) 
	values (_numero_richiesta,true,(case when get_json_valore(output_gisa, 'Esito')='OK' then true when get_json_valore(output_gisa, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_gisa_ext, 'Esito')='OK' then true when get_json_valore(output_gisa_ext, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_bdu, 'Esito')='OK' then true when get_json_valore(output_bdu, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_vam, 'Esito')='OK' then true when get_json_valore(output_vam, 'Esito')='KO' then false else null end),
			       (case when get_json_valore(output_digemon, 'Esito')='OK' then true when get_json_valore(output_digemon, 'Esito')='KO' then false else null end),
					       _id_utente, (case when esito = 1 then 1 end)
						,esito_processa);					
		   
	return esito_processa;
	
END
$$;


ALTER FUNCTION spid.processa_richiesta_elimina(_numero_richiesta text, _id_utente integer) OWNER TO postgres;

--
-- Name: processa_richiesta_insert(text, integer); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.processa_richiesta_insert(_numero_richiesta text, _id_utente integer) RETURNS text
    LANGUAGE plpgsql STRICT
    AS $$
DECLARE
	--r RECORD;
	giava integer;
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
	giava := -1;
	username_out := '';
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

	check_gisa := '';
	check_gisa_ext := '';
	check_bdu := '';
	check_vam := '';
	check_digemon := '';

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

	IF length(check_gisa)>0 THEN
	ep_guc := 'KO;;KO';
	output_gisa := '{"Esito":"KO", "DescrizioneErrore":"'||check_gisa||'"}';
	END IF;
	IF length(check_gisa_ext)>0 THEN
	ep_guc := 'KO;;KO';
	output_gisa_ext := '{"Esito":"KO", "DescrizioneErrore":"'||check_gisa_ext||'"}';
	END IF;
	IF length(check_bdu)>0 THEN
	ep_guc := 'KO;;KO';
	output_bdu := '{"Esito":"KO", "DescrizioneErrore":"'||check_bdu||'"}';
	END IF;
	IF length(check_vam)>0 THEN
	ep_guc := 'KO;;KO';
	output_vam := '{"Esito":"KO", "DescrizioneErrore":"'||check_vam||'"}';
	END IF;
	IF length(check_digemon)>0 THEN
	ep_guc := 'KO;;KO';
	output_digemon := '{"Esito":"KO", "DescrizioneErrore":"'||check_digemon||'"}';
	END IF;
	
	if (length(check_gisa)=0 and length(check_gisa_ext) = 0 and length(check_bdu) = 0 and length(check_vam) = 0 and length(check_digemon) = 0) then 
	
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
			query := (select concat(
			concat('select * from dbi_insert_utente_extended_opt(',
			'''', (select id from guc_utenti where username = username_out), '''', ', ',
			'''', (select COALESCE((select coalesce(in_nucleo::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
			'''', 'true', '''', ', ',
			'''', (select COALESCE((select coalesce(in_dpat::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
			'''', 'false', '''', ', ',
			'''', 'false', 
			'''', '); ')) );
			query := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
		end if;

		if (ep_gisa_ext > 0 and esito_guc = 'OK') then
			query_endpoint := (select * from spid.get_dbi_per_endpoint(_numero_richiesta, 'gisa_ext', 'insert', _id_utente, username_out, password_out, null::timestamp without time zone,-1, -1));
			output_gisa_ext := (select * from spid.esegui_query(query_endpoint, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
			output_gisa_ext := '{"Esito" : "'||output_gisa_ext ||'"}';
			giava := (select id_ruolo_gisa_ext from spid.spid_registrazioni s where s.numero_richiesta = _numero_richiesta);
			query :=(select concat(
			concat('select * from dbi_insert_utente_extended_opt(',
				'''', (select id from guc_utenti where username = username_out), '''', ', ',
				'''', 'false', '''', ', ',
				'''', 'false', '''', ', ',
				'''', 'false', '''', ', ',
				'''', (select COALESCE((select coalesce(in_nucleo::text, 'false') from spid.spid_registrazioni_flag where trashed_date is null and numero_richiesta = _numero_richiesta), 'false')), '''', ', ',
				'''', (case when giava =10000008 then 'false' else 'true' end), 
				'''', '); ')) );
			query := (select * from spid.esegui_query(query, 'guc', _id_utente,''));
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

		END IF;

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
				query = (select concat('SELECT * from dbi_insert_log_user_reg(''', (select replace(coalesce(nome,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select replace(coalesce(cognome,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', 
				(select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select replace(coalesce(indirizzo,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', 
				(select coalesce(id_gestore_acque,-1) from spid.get_lista_richieste(_numero_richiesta)), ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', 
				(select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), 
				''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', 'ACQUE', ''')'));
				raise info 'stampa query insert gisa_ext acque%', query;
				query := (select * from spid.esegui_query(query, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
				raise info 'output query gisa_ext per insert acque: %', query;
			end if;
			-- gestione ruolo apicoltore autoconsumo/commercio
			if ep_gisa_ext = 10000002 then 
				raise info 'inserisco una richiesta per apicoltura';
				query = (select concat('SELECT * from dbi_insert_log_user_reg(''', (select replace(coalesce(nome,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select replace(coalesce(cognome,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', (select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select replace(coalesce(indirizzo,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', -1, ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', 
				(select piva_numregistrazione from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', 'API', ''')'));
				raise info 'stampa query insert gisa_ext api%', query;
				query := (select * from spid.esegui_query(query, 'gisa', _id_utente,'host=dbGISAL dbname=gisa'));
				raise info 'output query gisa_ext per insert apicoltura: %', query;							
			end if;
			
			-- gestione ruolo dist e trasportatore
			if ep_gisa_ext = 10000004 then 
				query := (select concat('SELECT * from dbi_insert_log_user_reg(''', (select replace(coalesce(nome,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				(select replace(coalesce(cognome,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select codice_fiscale from spid.get_lista_richieste(_numero_richiesta)), ''', ''', '', ''', ''', 
				(select email from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(istat_comune,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''',
				 (select coalesce(lower(provincia_ordine_vet),'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select coalesce(cap,'') from spid.get_lista_richieste(_numero_richiesta)), ''', ''',
				  (select replace(coalesce(indirizzo,''), '''', '') from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select telefono from spid.get_lista_richieste(_numero_richiesta)), ''', ''', 
				  (select ip from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''',', -1, ', ''', '', ''', ', 'null::timestamp with time zone', ', ''', 
				  (select codice_gisa from spid.get_lista_richieste(_numero_richiesta)), ''', ''', (select user_agent from spid.spid_registrazioni where numero_richiesta = _numero_richiesta), ''', ''', (select case when id_tipologia_trasp_dist = 1 then 'TRASPORTATORE' when id_tipologia_trasp_dist = 2 then 'DISTRIBUTORE' else '' end from spid.get_lista_richieste(_numero_richiesta)), ''')'));
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
						

     RETURN esito_processa;

 END;
$$;


ALTER FUNCTION spid.processa_richiesta_insert(_numero_richiesta text, _id_utente integer) OWNER TO postgres;

--
-- Name: processa_richiesta_modifica(text, integer); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.processa_richiesta_modifica(_numero_richiesta text, _idutente integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
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
			output_gisa := COALESCE(output_guc_gisa, '');

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
					--else
			--output_gisa := output_guc_gisa;
				end if;
			end if;	
	
	end if; -- fine ep_gisa

	if (ep_gisa_ext > 0 and _endpoint_guc_ruoli ilike 'gisa_ext') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''Gisa_ext'')';
			output_guc_gisa_ext := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_gisa_ext := COALESCE(output_guc_gisa_ext, '');

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
					--else
			--output_gisa_ext := output_guc_gisa_ext;
				end if;
			end if;			        
			
	end if; -- fine ep_gisa_ext

	if (ep_bdu > 0 and _endpoint_guc_ruoli ilike 'bdu') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''bdu'')';
			output_guc_bdu := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_bdu := COALESCE(output_guc_bdu, '');
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
					--else
			--output_bdu := output_guc_bdu;
				end if;
			end if;		
		
	end if; -- fine ep_bdu
				  
	if (ep_vam > 0 and _endpoint_guc_ruoli ilike 'vam') then
		
			-- chiamo guc e gestisco l'output
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''vam'')';
			output_guc_vam := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_vam := COALESCE(output_guc_vam, '');
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
					--else
			--output_vam := output_guc_vam;
				end if;
			end if;		
		
	end if; -- fine ep_vam	  

	if (ep_digemon > 0 and _endpoint_guc_ruoli ilike 'digemon') then
		
			query  := 'select * from spid.elimina_ruolo_utente_guc('||_id_utente_guc_ruoli||','||_id_ruolo_guc_ruoli||',''digemon'')';
			output_guc_digemon := (select * from spid.esegui_query(query , 'guc', _idutente,''));
			output_digemon := COALESCE(output_guc_digemon, '');

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
					--else
			--output_digemon := output_guc_digemon;
				end if;
			end if;	
		
	end if; -- fine ep_digemon
	


	esito_processa = '{"EndPoint" : "GUC", "Username" : "' || username_out || '", "CodiceFiscale": "'||_codice_fiscale||'","ListaEndPoint" : [
					{"EndPoint" : "GISA", "Risultato" : ['||output_gisa||']},
					{"EndPoint" : "GISA_EXT", "Risultato" : ['||output_gisa_ext||']},
					{"EndPoint" : "BDR", "Risultato" : ['||output_bdu||']},
					{"EndPoint" : "VAM", "Risultato" : ['||output_vam||']},
					{"EndPoint" : "DIGEMON", "Risultato" : ['||output_digemon||']}]}';
	raise info '[STAMPA ESITO PROCESSA] %', esito_processa;

	IF get_json_valore(output_gisa, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_gisa_ext, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_bdu, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_vam, 'Esito')='OK' THEN
		esito = 1;
	END IF;
	IF get_json_valore(output_digemon, 'Esito')='OK' THEN
		esito = 1;
	END IF;
		
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
$$;


ALTER FUNCTION spid.processa_richiesta_modifica(_numero_richiesta text, _idutente integer) OWNER TO postgres;

--
-- Name: rifiuta_richiesta(text, integer); Type: FUNCTION; Schema: spid; Owner: postgres
--

CREATE FUNCTION spid.rifiuta_richiesta(_numero_richiesta text, _id_utente integer) RETURNS text
    LANGUAGE plpgsql
    AS $$
declare
 _id integer;
BEGIN

UPDATE spid.spid_registrazioni_esiti set id_utente_esito= _id_utente,  stato=2 where numero_richiesta = _numero_richiesta and trashed_date is null;
insert into spid.spid_registrazioni_esiti(numero_richiesta, stato,id_utente_esito)  values (_numero_richiesta,2, _id_utente);
return '{"Esito" : "OK"}';
END
$$;


ALTER FUNCTION spid.rifiuta_richiesta(_numero_richiesta text, _id_utente integer) OWNER TO postgres;

--
-- Name: foreign_server_bdu; Type: SERVER; Schema: -; Owner: postgres
--

CREATE SERVER foreign_server_bdu FOREIGN DATA WRAPPER postgres_fdw OPTIONS (
    dbname 'bdu',
    host '172.16.0.3',
    port '5432'
);


ALTER SERVER foreign_server_bdu OWNER TO postgres;

--
-- Name: USER MAPPING postgres SERVER foreign_server_bdu; Type: USER MAPPING; Schema: -; Owner: postgres
--

CREATE USER MAPPING FOR postgres SERVER foreign_server_bdu OPTIONS (
    password 'postgres',
    "user" 'postgres'
);


--
-- Name: foreign_server_bdu_priv; Type: SERVER; Schema: -; Owner: postgres
--

CREATE SERVER foreign_server_bdu_priv FOREIGN DATA WRAPPER postgres_fdw OPTIONS (
    dbname 'bdu',
    host '172.16.0.2',
    port '5432'
);


ALTER SERVER foreign_server_bdu_priv OWNER TO postgres;

--
-- Name: USER MAPPING postgres SERVER foreign_server_bdu_priv; Type: USER MAPPING; Schema: -; Owner: postgres
--

CREATE USER MAPPING FOR postgres SERVER foreign_server_bdu_priv OPTIONS (
    password 'postgres',
    "user" 'postgres'
);


SET default_tablespace = '';

--
-- Name: asl; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.asl (
    id integer NOT NULL,
    nome character varying(255),
    id_vam integer
);


ALTER TABLE public.asl OWNER TO postgres;

--
-- Name: capability; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.capability (
    id bigint NOT NULL,
    category_name character varying(255),
    secureobject_name character varying(255),
    subject_name character varying(255)
);


ALTER TABLE public.capability OWNER TO postgres;

--
-- Name: capability_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.capability_permission (
    capabilities_id bigint NOT NULL,
    permissions_name character varying(255) NOT NULL
);


ALTER TABLE public.capability_permission OWNER TO postgres;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    name character varying(255) NOT NULL
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: category_secureobject; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category_secureobject (
    categories_name character varying(255) NOT NULL,
    secureobjects_name character varying(255) NOT NULL
);


ALTER TABLE public.category_secureobject OWNER TO postgres;

--
-- Name: codice_raggruppamento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.codice_raggruppamento_id_seq
    START WITH 43
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.codice_raggruppamento_id_seq OWNER TO postgres;

--
-- Name: comuni; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.comuni (
    comune character varying(80) NOT NULL,
    codice character varying(50) NOT NULL,
    codiceistatcomune character varying(50),
    codiceistatregione character varying(50),
    codiceistatasl character varying(50),
    codiceistatasl_old character varying(50),
    codice_old character varying(50)
);


ALTER TABLE public.comuni OWNER TO postgres;

--
-- Name: extended_option; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.extended_option (
    id integer NOT NULL,
    key text,
    user_id integer,
    endpoint text,
    val text,
    username_temp text
);


ALTER TABLE public.extended_option OWNER TO postgres;

--
-- Name: extended_option_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.extended_option_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.extended_option_id_seq OWNER TO postgres;

--
-- Name: extended_option_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.extended_option_id_seq OWNED BY public.extended_option.id;


--
-- Name: guc_canili_bdu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_canili_bdu (
    id integer NOT NULL,
    id_canile integer,
    descrizione_canile text,
    id_utente integer
);


ALTER TABLE public.guc_canili_bdu OWNER TO postgres;

--
-- Name: guc_canili_bdu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_canili_bdu_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_canili_bdu_id_seq OWNER TO postgres;

--
-- Name: guc_canili_bdu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_canili_bdu_id_seq OWNED BY public.guc_canili_bdu.id;


--
-- Name: guc_cf_white_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_cf_white_list (
    codice_fiscale character varying(16),
    password text
);


ALTER TABLE public.guc_cf_white_list OWNER TO postgres;

--
-- Name: guc_cliniche_vam; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_cliniche_vam (
    id integer NOT NULL,
    id_clinica integer,
    descrizione_clinica text,
    id_utente integer
);


ALTER TABLE public.guc_cliniche_vam OWNER TO postgres;

--
-- Name: guc_cliniche_vam_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_cliniche_vam_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_cliniche_vam_id_seq OWNER TO postgres;

--
-- Name: guc_cliniche_vam_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_cliniche_vam_id_seq OWNED BY public.guc_cliniche_vam.id;


--
-- Name: guc_endpoint; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_endpoint (
    id integer NOT NULL,
    nome text NOT NULL,
    ds_master text,
    ds_slave text,
    enabled boolean DEFAULT true
);


ALTER TABLE public.guc_endpoint OWNER TO postgres;

--
-- Name: guc_endpoint_connector_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_endpoint_connector_config (
    id integer NOT NULL,
    id_operazione integer NOT NULL,
    id_endpoint integer NOT NULL,
    sql text,
    firma_sql text,
    url_reload_utenti text,
    firma_url_reload_utenti text,
    enabled boolean DEFAULT true
);


ALTER TABLE public.guc_endpoint_connector_config OWNER TO postgres;

--
-- Name: guc_endpoint_connector_config_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_endpoint_connector_config_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_endpoint_connector_config_id_seq OWNER TO postgres;

--
-- Name: guc_endpoint_connector_config_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_endpoint_connector_config_id_seq OWNED BY public.guc_endpoint_connector_config.id;


--
-- Name: guc_endpoint_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_endpoint_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_endpoint_id_seq OWNER TO postgres;

--
-- Name: guc_endpoint_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_endpoint_id_seq OWNED BY public.guc_endpoint.id;


--
-- Name: guc_importatori; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_importatori (
    id integer NOT NULL,
    id_importatore integer,
    descrizione_importatore text,
    id_utente integer,
    partita_iva text
);


ALTER TABLE public.guc_importatori OWNER TO postgres;

--
-- Name: guc_importatori_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_importatori_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_importatori_id_seq OWNER TO postgres;

--
-- Name: guc_importatori_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_importatori_id_seq OWNED BY public.guc_importatori.id;


--
-- Name: guc_operazioni; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_operazioni (
    id integer NOT NULL,
    nome text NOT NULL,
    enabled boolean DEFAULT true
);


ALTER TABLE public.guc_operazioni OWNER TO postgres;

--
-- Name: guc_operazioni_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_operazioni_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_operazioni_id_seq OWNER TO postgres;

--
-- Name: guc_operazioni_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_operazioni_id_seq OWNED BY public.guc_operazioni.id;


--
-- Name: guc_ruoli; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_ruoli (
    id integer NOT NULL,
    endpoint character varying(255),
    ruolo_integer integer,
    ruolo_string character varying(255),
    id_utente integer,
    note text,
    trashed boolean,
    old_ruolo_integer integer,
    old_ruolo_string text,
    username_temp text,
    codice_raggruppamento integer,
    data_scadenza timestamp without time zone
);


ALTER TABLE public.guc_ruoli OWNER TO postgres;

--
-- Name: guc_ruoli_appo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_ruoli_appo (
    id integer NOT NULL,
    username text NOT NULL,
    endpoint text NOT NULL,
    ruolo_id integer DEFAULT '-1'::integer NOT NULL,
    ruolo_descrizione text DEFAULT 'N.D.'::text NOT NULL
);


ALTER TABLE public.guc_ruoli_appo OWNER TO postgres;

--
-- Name: guc_ruoli_appo_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_ruoli_appo_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_ruoli_appo_id_seq OWNER TO postgres;

--
-- Name: guc_ruoli_appo_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_ruoli_appo_id_seq OWNED BY public.guc_ruoli_appo.id;


--
-- Name: guc_ruoli_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_ruoli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_ruoli_id_seq OWNER TO postgres;

--
-- Name: guc_ruoli_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_ruoli_id_seq OWNED BY public.guc_ruoli.id;


--
-- Name: guc_strutture_gisa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_strutture_gisa (
    id integer NOT NULL,
    id_struttura integer,
    descrizione_struttura text,
    id_utente integer,
    id_padre integer,
    n_livello integer
);


ALTER TABLE public.guc_strutture_gisa OWNER TO postgres;

--
-- Name: guc_strutture_gisa_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_strutture_gisa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_strutture_gisa_id_seq OWNER TO postgres;

--
-- Name: guc_strutture_gisa_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_strutture_gisa_id_seq OWNED BY public.guc_strutture_gisa.id;


--
-- Name: guc_utenti_; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_utenti_ (
    id integer NOT NULL,
    clinica_description character varying(255),
    clinica_id integer,
    codice_fiscale character varying(255),
    cognome character varying(255),
    email character varying(255),
    enabled boolean,
    entered timestamp without time zone,
    enteredby integer,
    expires timestamp without time zone,
    modified timestamp without time zone,
    modifiedby integer,
    nome character varying(255),
    note text,
    password character varying(255),
    username character varying(255),
    asl_id integer,
    canile_id integer,
    canile_description character varying(255),
    password_encrypted character varying(255),
    luogo text,
    num_autorizzazione text,
    importatori_description text,
    canilebdu_id integer,
    canilebdu_description text,
    id_importatore integer,
    id_provincia_iscrizione_albo_vet_privato integer,
    nr_iscrione_albo_vet_privato text,
    password2 character varying(255),
    data_scadenza timestamp without time zone,
    id_utente_guc_old integer,
    cessato boolean DEFAULT false,
    data_fine_validita date,
    suap_ip_address text,
    suap_istat_comune text,
    suap_pec text,
    suap_callback text,
    suap_shared_key text,
    suap_callback_ko text,
    num_registrazione_stab text,
    suap_livello_accreditamento integer,
    suap_descrizione_livello_accreditamento text,
    telefono text,
    note_internal_use_only text,
    note_internal_use_only_hd text,
    luogo_vam text,
    id_provincia_iscrizione_albo_vet_privato_vam integer,
    nr_iscrione_albo_vet_privato_vam text,
    gestore_acque integer,
    comune_gestore_acque integer,
    piva character(11),
    comune_apicoltore integer,
    comune_trasportatore integer,
    indirizzo_trasportatore text,
    indirizzo_apicoltore text,
    cap_trasportatore text,
    cap_apicoltore text,
    tipo_attivita_apicoltore text
);


ALTER TABLE public.guc_utenti_ OWNER TO postgres;

--
-- Name: guc_utenti; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.guc_utenti AS
 SELECT aa.id,
    aa.clinica_description,
    aa.clinica_id,
    aa.codice_fiscale,
    aa.cognome,
    aa.email,
    aa.enabled,
    aa.entered,
    aa.enteredby,
    aa.expires,
    aa.modified,
    aa.modifiedby,
    aa.nome,
    aa.note,
    aa.password,
    aa.username,
    aa.asl_id,
    aa.canile_id,
    aa.canile_description,
    aa.password_encrypted,
    aa.luogo,
    aa.num_autorizzazione,
    aa.importatori_description,
    aa.canilebdu_id,
    aa.canilebdu_description,
    aa.id_importatore,
    aa.id_provincia_iscrizione_albo_vet_privato,
    aa.nr_iscrione_albo_vet_privato,
    aa.password2,
    aa.data_fine_validita,
    aa.data_scadenza,
    aa.id_utente_guc_old,
    aa.suap_ip_address,
    aa.suap_istat_comune,
    aa.suap_pec,
    aa.suap_callback,
    aa.suap_shared_key,
    aa.suap_callback_ko,
    aa.num_registrazione_stab,
    aa.suap_livello_accreditamento,
    aa.telefono,
    aa.luogo_vam,
    aa.id_provincia_iscrizione_albo_vet_privato_vam,
    aa.nr_iscrione_albo_vet_privato_vam,
    aa.gestore_acque,
    aa.comune_gestore_acque,
    aa.piva,
    aa.comune_apicoltore,
    aa.comune_trasportatore,
    aa.indirizzo_trasportatore,
    aa.indirizzo_apicoltore,
    aa.cap_trasportatore,
    aa.cap_apicoltore,
    aa.tipo_attivita_apicoltore
   FROM ( SELECT DISTINCT ON (a.username) a.username AS username_,
            a.id,
            a.clinica_description,
            a.clinica_id,
            a.codice_fiscale,
            a.cognome,
            a.email,
            a.enabled,
            a.entered,
            a.enteredby,
            a.expires,
            a.modified,
            a.modifiedby,
            a.nome,
            a.note,
            a.password,
            a.username,
            a.asl_id,
            a.canile_id,
            a.canile_description,
            a.password_encrypted,
            a.luogo,
            a.num_autorizzazione,
            a.importatori_description,
            a.canilebdu_id,
            a.canilebdu_description,
            a.id_importatore,
            a.id_provincia_iscrizione_albo_vet_privato,
            a.nr_iscrione_albo_vet_privato,
            a.password2,
            a.data_fine_validita,
            a.data_scadenza,
            a.id_utente_guc_old,
            a.suap_ip_address,
            a.suap_istat_comune,
            a.suap_pec,
            a.suap_callback,
            a.suap_shared_key,
            a.suap_callback_ko,
            a.num_registrazione_stab,
            a.suap_livello_accreditamento,
            a.telefono,
            a.luogo_vam,
            a.id_provincia_iscrizione_albo_vet_privato_vam,
            a.nr_iscrione_albo_vet_privato_vam,
            a.gestore_acque,
            a.comune_gestore_acque,
            a.piva,
            a.comune_apicoltore,
            a.comune_trasportatore,
            a.indirizzo_trasportatore,
            a.indirizzo_apicoltore,
            a.cap_trasportatore,
            a.cap_apicoltore,
            a.tipo_attivita_apicoltore
           FROM ( SELECT guc_utenti_.id,
                    guc_utenti_.clinica_description,
                    guc_utenti_.clinica_id,
                    guc_utenti_.codice_fiscale,
                    guc_utenti_.cognome,
                    guc_utenti_.email,
                    guc_utenti_.enabled,
                    guc_utenti_.entered,
                    guc_utenti_.enteredby,
                    guc_utenti_.expires,
                    guc_utenti_.modified,
                    guc_utenti_.modifiedby,
                    guc_utenti_.nome,
                    guc_utenti_.note,
                    guc_utenti_.password,
                    guc_utenti_.username,
                    guc_utenti_.asl_id,
                    guc_utenti_.canile_id,
                    guc_utenti_.canile_description,
                    guc_utenti_.password_encrypted,
                    guc_utenti_.luogo,
                    guc_utenti_.num_autorizzazione,
                    guc_utenti_.importatori_description,
                    guc_utenti_.canilebdu_id,
                    guc_utenti_.canilebdu_description,
                    guc_utenti_.id_importatore,
                    guc_utenti_.id_provincia_iscrizione_albo_vet_privato,
                    guc_utenti_.nr_iscrione_albo_vet_privato,
                    guc_utenti_.password2,
                    guc_utenti_.data_fine_validita,
                    guc_utenti_.data_scadenza,
                    guc_utenti_.id_utente_guc_old,
                    guc_utenti_.suap_ip_address,
                    guc_utenti_.suap_istat_comune,
                    guc_utenti_.suap_pec,
                    guc_utenti_.suap_callback,
                    guc_utenti_.suap_shared_key,
                    guc_utenti_.suap_callback_ko,
                    guc_utenti_.num_registrazione_stab,
                    guc_utenti_.suap_descrizione_livello_accreditamento,
                    guc_utenti_.suap_livello_accreditamento,
                    guc_utenti_.telefono,
                    guc_utenti_.luogo_vam,
                    guc_utenti_.id_provincia_iscrizione_albo_vet_privato_vam,
                    guc_utenti_.nr_iscrione_albo_vet_privato_vam,
                    guc_utenti_.gestore_acque,
                    guc_utenti_.comune_gestore_acque,
                    guc_utenti_.piva,
                    guc_utenti_.comune_apicoltore,
                    guc_utenti_.comune_trasportatore,
                    guc_utenti_.indirizzo_trasportatore,
                    guc_utenti_.indirizzo_apicoltore,
                    guc_utenti_.cap_trasportatore,
                    guc_utenti_.cap_apicoltore,
                    guc_utenti_.tipo_attivita_apicoltore
                   FROM public.guc_utenti_
                  WHERE (((guc_utenti_.data_scadenza > ((('now'::text)::date)::text)::date) OR (guc_utenti_.data_scadenza IS NULL)) AND guc_utenti_.enabled)) a
          ORDER BY a.username, a.data_scadenza) aa;


ALTER TABLE public.guc_utenti OWNER TO postgres;

--
-- Name: guc_utenti_appo; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_utenti_appo (
    username text NOT NULL,
    password text,
    asl integer DEFAULT '-1'::integer NOT NULL,
    nome text,
    cognome text,
    codice_fiscale text,
    email text,
    note text,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT now() NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT now() NOT NULL,
    enabled boolean DEFAULT true NOT NULL,
    clinica integer DEFAULT '-1'::integer,
    clinica_descrizione text,
    expires timestamp(3) without time zone
);


ALTER TABLE public.guc_utenti_appo OWNER TO postgres;

--
-- Name: guc_utenti_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.guc_utenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.guc_utenti_id_seq OWNER TO postgres;

--
-- Name: guc_utenti_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.guc_utenti_id_seq OWNED BY public.guc_utenti_.id;


--
-- Name: guc_utenti_new; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.guc_utenti_new (
    clinica_description character varying(255),
    clinica_id integer,
    codice_fiscale character varying(255),
    cognome character varying(255),
    email character varying(255),
    enabled boolean,
    entered timestamp without time zone,
    enteredby integer,
    expires timestamp without time zone,
    modified timestamp without time zone,
    modifiedby integer,
    nome character varying(255),
    note character varying(255),
    password character varying(255),
    username character varying(255),
    asl_id integer,
    canile_id integer,
    canile_description character varying(255)
);


ALTER TABLE public.guc_utenti_new OWNER TO postgres;

--
-- Name: hex_password_encrypt_decrypt_servlet; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.hex_password_encrypt_decrypt_servlet (
    key text NOT NULL
);


ALTER TABLE public.hex_password_encrypt_decrypt_servlet OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 205
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: listaruoli; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.listaruoli AS
 SELECT guc_ruoli.id,
    guc_ruoli.endpoint,
    guc_ruoli.ruolo_integer,
    guc_ruoli.ruolo_string,
    guc_ruoli.id_utente,
    guc_ruoli.note,
    guc_ruoli.trashed
   FROM public.guc_ruoli
  WHERE ((guc_ruoli.trashed = false) OR (guc_ruoli.trashed IS NULL));


ALTER TABLE public.listaruoli OWNER TO postgres;

--
-- Name: log_reload_utenti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_reload_utenti (
    id integer NOT NULL,
    endpoint text,
    url_invocata text,
    id_utente integer,
    username text,
    data_chiamata timestamp without time zone,
    response text,
    tipo_op text
);


ALTER TABLE public.log_reload_utenti OWNER TO postgres;

--
-- Name: log_reload_utenti_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.log_reload_utenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.log_reload_utenti_id_seq OWNER TO postgres;

--
-- Name: log_reload_utenti_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.log_reload_utenti_id_seq OWNED BY public.log_reload_utenti.id;


--
-- Name: log_ruoli_utenti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_ruoli_utenti (
    id integer NOT NULL,
    id_log_utente integer NOT NULL,
    endpoint character varying(255),
    ruolo character varying(255),
    dbi text
);


ALTER TABLE public.log_ruoli_utenti OWNER TO postgres;

--
-- Name: log_ruoli_utenti_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.log_ruoli_utenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.log_ruoli_utenti_id_seq OWNER TO postgres;

--
-- Name: log_ruoli_utenti_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.log_ruoli_utenti_id_seq OWNED BY public.log_ruoli_utenti.id;


--
-- Name: log_utenti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.log_utenti (
    id integer NOT NULL,
    id_utente integer NOT NULL,
    username character varying(255),
    nome character varying(255),
    cognome character varying(255),
    password character varying(255),
    password_encrypted character varying(255),
    operazione character varying(255),
    data timestamp without time zone,
    id_modificante integer,
    username_modificante character varying(255)
);


ALTER TABLE public.log_utenti OWNER TO postgres;

--
-- Name: log_utenti_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.log_utenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.log_utenti_id_seq OWNER TO postgres;

--
-- Name: log_utenti_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.log_utenti_id_seq OWNED BY public.log_utenti.id;


--
-- Name: permessi_funzione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permessi_funzione (
    id_funzione integer NOT NULL,
    descrizione character varying(255),
    nome character varying(255)
);


ALTER TABLE public.permessi_funzione OWNER TO postgres;

--
-- Name: permessi_funzione_id_funzione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.permessi_funzione_id_funzione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permessi_funzione_id_funzione_seq OWNER TO postgres;

--
-- Name: permessi_funzione_id_funzione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.permessi_funzione_id_funzione_seq OWNED BY public.permessi_funzione.id_funzione;


--
-- Name: permessi_gui; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permessi_gui (
    id_gui integer NOT NULL,
    descrizione character varying(255),
    nome character varying(255),
    id_subfunzione integer
);


ALTER TABLE public.permessi_gui OWNER TO postgres;

--
-- Name: permessi_gui_id_gui_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.permessi_gui_id_gui_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permessi_gui_id_gui_seq OWNER TO postgres;

--
-- Name: permessi_gui_id_gui_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.permessi_gui_id_gui_seq OWNED BY public.permessi_gui.id_gui;


--
-- Name: permessi_subfunzione; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permessi_subfunzione (
    id_subfunzione integer NOT NULL,
    descrizione character varying(255),
    nome character varying(255),
    id_funzione integer
);


ALTER TABLE public.permessi_subfunzione OWNER TO postgres;

--
-- Name: permessi_gui_view; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.permessi_gui_view AS
 SELECT f.id_funzione,
    f.nome AS nome_funzione,
    f.descrizione AS descrizione_funzione,
    s.id_subfunzione,
    s.nome AS nome_subfunzione,
    s.descrizione AS descrizione_subfunzione,
    g.id_gui,
    g.nome AS nome_gui,
    g.descrizione AS descrizione_gui
   FROM ((public.permessi_funzione f
     JOIN public.permessi_subfunzione s ON ((f.id_funzione = s.id_funzione)))
     JOIN public.permessi_gui g ON ((s.id_subfunzione = g.id_subfunzione)));


ALTER TABLE public.permessi_gui_view OWNER TO postgres;

--
-- Name: permessi_ruoli; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permessi_ruoli (
    id integer NOT NULL,
    descrizione character varying(255),
    nome character varying(255)
);


ALTER TABLE public.permessi_ruoli OWNER TO postgres;

--
-- Name: permessi_ruoli_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.permessi_ruoli_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permessi_ruoli_id_seq OWNER TO postgres;

--
-- Name: permessi_ruoli_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.permessi_ruoli_id_seq OWNED BY public.permessi_ruoli.id;


--
-- Name: permessi_subfunzione_id_subfunzione_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.permessi_subfunzione_id_subfunzione_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.permessi_subfunzione_id_subfunzione_seq OWNER TO postgres;

--
-- Name: permessi_subfunzione_id_subfunzione_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.permessi_subfunzione_id_subfunzione_seq OWNED BY public.permessi_subfunzione.id_subfunzione;


--
-- Name: permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permission (
    type character varying(31) NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.permission OWNER TO postgres;

--
-- Name: permission_permission; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.permission_permission (
    grouppermissions_name character varying(255) NOT NULL,
    simplepermissions_name character varying(255) NOT NULL
);


ALTER TABLE public.permission_permission OWNER TO postgres;

--
-- Name: remote_bdu_access_; Type: FOREIGN TABLE; Schema: public; Owner: postgres
--

CREATE FOREIGN TABLE public.remote_bdu_access_ (
    user_id integer,
    username character varying(80) NOT NULL,
    password character varying(80),
    contact_id integer DEFAULT '-1'::integer,
    role_id integer DEFAULT '-1'::integer,
    manager_id integer DEFAULT '-1'::integer,
    startofday integer DEFAULT 8,
    endofday integer DEFAULT 18,
    locale character varying(255),
    timezone character varying(255) DEFAULT 'America/New_York'::character varying,
    last_ip character varying(30),
    last_login timestamp(3) without time zone DEFAULT now() NOT NULL,
    enteredby integer NOT NULL,
    entered timestamp(3) without time zone DEFAULT now() NOT NULL,
    modifiedby integer NOT NULL,
    modified timestamp(3) without time zone DEFAULT now() NOT NULL,
    expires timestamp(3) without time zone DEFAULT NULL::timestamp without time zone,
    alias integer DEFAULT '-1'::integer,
    assistant integer DEFAULT '-1'::integer,
    enabled boolean DEFAULT true NOT NULL,
    currency character varying(5),
    language character varying(20),
    webdav_password character varying(80),
    hidden boolean DEFAULT false,
    site_id integer,
    allow_webdav_access boolean DEFAULT true NOT NULL,
    allow_httpapi_access boolean DEFAULT true NOT NULL,
    temp_password character varying(80),
    temp_webdav_password character varying(80),
    id_linea_produttiva_riferimento integer,
    id_importatore integer,
    comune integer,
    provincia character varying(100),
    access_position_lat double precision,
    access_position_lon double precision,
    access_position_err text,
    trashed_date timestamp without time zone,
    last_interaction_time timestamp(3) without time zone,
    action character varying(255),
    command character varying(255),
    object_id integer,
    table_name character varying(255),
    data_scadenza timestamp without time zone,
    cessato boolean DEFAULT false
)
SERVER foreign_server_bdu
OPTIONS (
    schema_name 'public',
    table_name 'access_'
);


ALTER FOREIGN TABLE public.remote_bdu_access_ OWNER TO postgres;

--
-- Name: sca_storico_operazioni_utenti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.sca_storico_operazioni_utenti (
    user_id integer NOT NULL,
    username character varying(255) NOT NULL,
    ip character varying(20) NOT NULL,
    data timestamp without time zone NOT NULL,
    path text NOT NULL,
    automatico boolean DEFAULT false NOT NULL,
    id integer NOT NULL,
    parametri text,
    browser text,
    action text
);


ALTER TABLE public.sca_storico_operazioni_utenti OWNER TO postgres;

--
-- Name: secureobject; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.secureobject (
    name character varying(255) NOT NULL
);


ALTER TABLE public.secureobject OWNER TO postgres;

--
-- Name: sirv_storico_operazioni_utenti_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.sirv_storico_operazioni_utenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sirv_storico_operazioni_utenti_id_seq OWNER TO postgres;

--
-- Name: sirv_storico_operazioni_utenti_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.sirv_storico_operazioni_utenti_id_seq OWNED BY public.sca_storico_operazioni_utenti.id;


--
-- Name: storico_cambio_password; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.storico_cambio_password (
    id integer NOT NULL,
    id_utente integer,
    ip_modifica text,
    data_modifica timestamp without time zone DEFAULT now(),
    vecchia_password text,
    nuova_password text,
    nuova_password_decript text
);


ALTER TABLE public.storico_cambio_password OWNER TO postgres;

--
-- Name: storico_cambio_password_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.storico_cambio_password_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.storico_cambio_password_id_seq OWNER TO postgres;

--
-- Name: storico_cambio_password_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.storico_cambio_password_id_seq OWNED BY public.storico_cambio_password.id;


--
-- Name: subject; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subject (
    name character varying(255) NOT NULL
);


ALTER TABLE public.subject OWNER TO postgres;

--
-- Name: utenti; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.utenti (
    id integer NOT NULL,
    cap character varying(255),
    codice_fiscale character varying(255),
    cognome character varying(255),
    comune character varying(255),
    comune_nascita character varying(255),
    data_nascita timestamp without time zone,
    data_scadenza timestamp without time zone,
    domanda_segreta character varying(255),
    email character varying(255),
    enabled boolean NOT NULL,
    enabled_date timestamp without time zone,
    entered timestamp without time zone,
    entered_by integer,
    fax character varying(255),
    indirizzo character varying(255),
    modified timestamp without time zone,
    modified_by integer,
    nome character varying(255),
    note character varying(255),
    password character varying(255),
    provincia character varying(255),
    risposta_segreta character varying(255),
    ruolo character varying(255),
    stato character varying(255),
    telefono1 character varying(255),
    telefono2 character varying(255),
    trashed_date timestamp without time zone,
    username character varying(255),
    super_admin boolean DEFAULT false,
    id_utente_guc_old integer
);


ALTER TABLE public.utenti OWNER TO postgres;

--
-- Name: utenti_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.utenti_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.utenti_id_seq OWNER TO postgres;

--
-- Name: utenti_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.utenti_id_seq OWNED BY public.utenti.id;


--
-- Name: html_registrazioni; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.html_registrazioni (
    id integer NOT NULL,
    id_spid_registrazioni bigint,
    html character varying
);


ALTER TABLE spid.html_registrazioni OWNER TO postgres;

--
-- Name: html_registrazioni_id_seq; Type: SEQUENCE; Schema: spid; Owner: postgres
--

CREATE SEQUENCE spid.html_registrazioni_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spid.html_registrazioni_id_seq OWNER TO postgres;

--
-- Name: html_registrazioni_id_seq; Type: SEQUENCE OWNED BY; Schema: spid; Owner: postgres
--

ALTER SEQUENCE spid.html_registrazioni_id_seq OWNED BY spid.html_registrazioni.id;


--
-- Name: log_query_generate; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.log_query_generate (
    id integer NOT NULL,
    host text,
    db text,
    query text,
    output text,
    enteredby integer,
    entered timestamp without time zone DEFAULT now()
);


ALTER TABLE spid.log_query_generate OWNER TO postgres;

--
-- Name: log_query_generate_id_seq; Type: SEQUENCE; Schema: spid; Owner: postgres
--

CREATE SEQUENCE spid.log_query_generate_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spid.log_query_generate_id_seq OWNER TO postgres;

--
-- Name: log_query_generate_id_seq; Type: SEQUENCE OWNED BY; Schema: spid; Owner: postgres
--

ALTER SEQUENCE spid.log_query_generate_id_seq OWNED BY spid.log_query_generate.id;


--
-- Name: lookup_tipo_registrazione_trasporti_distributori; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.lookup_tipo_registrazione_trasporti_distributori (
    id smallint,
    descr character varying
);


ALTER TABLE spid.lookup_tipo_registrazione_trasporti_distributori OWNER TO postgres;

--
-- Name: sca_storico_login; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.sca_storico_login (
    id integer NOT NULL,
    username text,
    endpoint text,
    codice_fiscale text,
    data_login timestamp without time zone DEFAULT now(),
    accesso_spid boolean
);


ALTER TABLE spid.sca_storico_login OWNER TO postgres;

--
-- Name: sca_storico_login_id_seq; Type: SEQUENCE; Schema: spid; Owner: postgres
--

CREATE SEQUENCE spid.sca_storico_login_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spid.sca_storico_login_id_seq OWNER TO postgres;

--
-- Name: sca_storico_login_id_seq; Type: SEQUENCE OWNED BY; Schema: spid; Owner: postgres
--

ALTER SEQUENCE spid.sca_storico_login_id_seq OWNED BY spid.sca_storico_login.id;


--
-- Name: spid_registrazioni; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.spid_registrazioni (
    id integer NOT NULL,
    id_tipologia_utente integer,
    id_tipo_richiesta integer,
    cognome character varying,
    nome character varying,
    codice_fiscale character varying,
    email character varying,
    telefono character varying,
    id_ruolo_gisa bigint,
    id_ruolo_bdu bigint,
    id_ruolo_vam bigint,
    id_clinica_vam integer[],
    id_ruolo_gisa_ext bigint,
    identificativo_ente character varying,
    piva_numregistrazione character varying,
    comune character varying,
    nominativo_referente character varying,
    ruolo_referente character varying,
    email_referente character varying,
    telefono_referente character varying,
    data_richiesta timestamp(0) without time zone,
    codice_gisa character varying,
    indirizzo character varying,
    id_gestore_acque bigint,
    cap character varying,
    pec character varying,
    giava boolean,
    digemon boolean,
    numero_richiesta character varying,
    id_ruolo_digemon bigint,
    provincia_ordine_vet character varying,
    numero_ordine_vet character varying,
    id_asl integer,
    numero_decreto_prefettizio character varying,
    scadenza_decreto_prefettizio character varying,
    ip character varying,
    user_agent character varying,
    numero_autorizzazione_regionale_vet character varying,
    id_tipologia_trasp_dist integer,
    id_guc_ruoli integer,
    CONSTRAINT lunghezzacf CHECK ((char_length((codice_fiscale)::text) = 16))
);


ALTER TABLE spid.spid_registrazioni OWNER TO postgres;

--
-- Name: spid_registrazioni_esiti; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.spid_registrazioni_esiti (
    id integer NOT NULL,
    numero_richiesta text,
    esito_guc boolean,
    esito_gisa boolean,
    esito_gisa_ext boolean,
    esito_bdu boolean,
    esito_vam boolean,
    esito_digemon boolean,
    data_esito timestamp without time zone DEFAULT now(),
    id_utente_esito integer,
    trashed_date timestamp without time zone,
    stato integer DEFAULT 0,
    json_esito text
);


ALTER TABLE spid.spid_registrazioni_esiti OWNER TO postgres;

--
-- Name: spid_registrazioni_esiti_id_seq; Type: SEQUENCE; Schema: spid; Owner: postgres
--

CREATE SEQUENCE spid.spid_registrazioni_esiti_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spid.spid_registrazioni_esiti_id_seq OWNER TO postgres;

--
-- Name: spid_registrazioni_esiti_id_seq; Type: SEQUENCE OWNED BY; Schema: spid; Owner: postgres
--

ALTER SEQUENCE spid.spid_registrazioni_esiti_id_seq OWNED BY spid.spid_registrazioni_esiti.id;


--
-- Name: spid_registrazioni_flag; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.spid_registrazioni_flag (
    id integer NOT NULL,
    numero_richiesta text,
    in_nucleo boolean,
    in_dpat boolean,
    trashed_date timestamp without time zone
);


ALTER TABLE spid.spid_registrazioni_flag OWNER TO postgres;

--
-- Name: spid_registrazioni_flag_id_seq; Type: SEQUENCE; Schema: spid; Owner: postgres
--

CREATE SEQUENCE spid.spid_registrazioni_flag_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spid.spid_registrazioni_flag_id_seq OWNER TO postgres;

--
-- Name: spid_registrazioni_flag_id_seq; Type: SEQUENCE OWNED BY; Schema: spid; Owner: postgres
--

ALTER SEQUENCE spid.spid_registrazioni_flag_id_seq OWNED BY spid.spid_registrazioni_flag.id;


--
-- Name: spid_registrazioni_id_seq; Type: SEQUENCE; Schema: spid; Owner: postgres
--

CREATE SEQUENCE spid.spid_registrazioni_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spid.spid_registrazioni_id_seq OWNER TO postgres;

--
-- Name: spid_registrazioni_id_seq; Type: SEQUENCE OWNED BY; Schema: spid; Owner: postgres
--

ALTER SEQUENCE spid.spid_registrazioni_id_seq OWNED BY spid.spid_registrazioni.id;


--
-- Name: spid_tipo_richiesta; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.spid_tipo_richiesta (
    id integer NOT NULL,
    descr text,
    title character varying
);


ALTER TABLE spid.spid_tipo_richiesta OWNER TO postgres;

--
-- Name: spid_tipo_richiesta_id_seq; Type: SEQUENCE; Schema: spid; Owner: postgres
--

CREATE SEQUENCE spid.spid_tipo_richiesta_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spid.spid_tipo_richiesta_id_seq OWNER TO postgres;

--
-- Name: spid_tipo_richiesta_id_seq; Type: SEQUENCE OWNED BY; Schema: spid; Owner: postgres
--

ALTER SEQUENCE spid.spid_tipo_richiesta_id_seq OWNED BY spid.spid_tipo_richiesta.id;


--
-- Name: spid_tipologia_utente; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.spid_tipologia_utente (
    id integer,
    descr text,
    ord smallint
);


ALTER TABLE spid.spid_tipologia_utente OWNER TO postgres;

--
-- Name: spid_tipologia_utente_ruoli_endpoint; Type: TABLE; Schema: spid; Owner: postgres
--

CREATE TABLE spid.spid_tipologia_utente_ruoli_endpoint (
    id integer NOT NULL,
    endpoint text,
    id_ruolo integer,
    id_tipologia_utente integer,
    enabled boolean DEFAULT true
);


ALTER TABLE spid.spid_tipologia_utente_ruoli_endpoint OWNER TO postgres;

--
-- Name: spid_tipologia_utente_ruoli_endpoint_id_seq; Type: SEQUENCE; Schema: spid; Owner: postgres
--

CREATE SEQUENCE spid.spid_tipologia_utente_ruoli_endpoint_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE spid.spid_tipologia_utente_ruoli_endpoint_id_seq OWNER TO postgres;

--
-- Name: spid_tipologia_utente_ruoli_endpoint_id_seq; Type: SEQUENCE OWNED BY; Schema: spid; Owner: postgres
--

ALTER SEQUENCE spid.spid_tipologia_utente_ruoli_endpoint_id_seq OWNED BY spid.spid_tipologia_utente_ruoli_endpoint.id;


--
-- Name: extended_option id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extended_option ALTER COLUMN id SET DEFAULT nextval('public.extended_option_id_seq'::regclass);


--
-- Name: guc_canili_bdu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_canili_bdu ALTER COLUMN id SET DEFAULT nextval('public.guc_canili_bdu_id_seq'::regclass);


--
-- Name: guc_cliniche_vam id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_cliniche_vam ALTER COLUMN id SET DEFAULT nextval('public.guc_cliniche_vam_id_seq'::regclass);


--
-- Name: guc_endpoint id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_endpoint ALTER COLUMN id SET DEFAULT nextval('public.guc_endpoint_id_seq'::regclass);


--
-- Name: guc_endpoint_connector_config id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_endpoint_connector_config ALTER COLUMN id SET DEFAULT nextval('public.guc_endpoint_connector_config_id_seq'::regclass);


--
-- Name: guc_importatori id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_importatori ALTER COLUMN id SET DEFAULT nextval('public.guc_importatori_id_seq'::regclass);


--
-- Name: guc_operazioni id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_operazioni ALTER COLUMN id SET DEFAULT nextval('public.guc_operazioni_id_seq'::regclass);


--
-- Name: guc_ruoli id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_ruoli ALTER COLUMN id SET DEFAULT nextval('public.guc_ruoli_id_seq'::regclass);


--
-- Name: guc_ruoli_appo id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_ruoli_appo ALTER COLUMN id SET DEFAULT nextval('public.guc_ruoli_appo_id_seq'::regclass);


--
-- Name: guc_strutture_gisa id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_strutture_gisa ALTER COLUMN id SET DEFAULT nextval('public.guc_strutture_gisa_id_seq'::regclass);


--
-- Name: guc_utenti_ id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_utenti_ ALTER COLUMN id SET DEFAULT nextval('public.guc_utenti_id_seq'::regclass);


--
-- Name: log_reload_utenti id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_reload_utenti ALTER COLUMN id SET DEFAULT nextval('public.log_reload_utenti_id_seq'::regclass);


--
-- Name: log_ruoli_utenti id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_ruoli_utenti ALTER COLUMN id SET DEFAULT nextval('public.log_ruoli_utenti_id_seq'::regclass);


--
-- Name: log_utenti id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_utenti ALTER COLUMN id SET DEFAULT nextval('public.log_utenti_id_seq'::regclass);


--
-- Name: permessi_funzione id_funzione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_funzione ALTER COLUMN id_funzione SET DEFAULT nextval('public.permessi_funzione_id_funzione_seq'::regclass);


--
-- Name: permessi_gui id_gui; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_gui ALTER COLUMN id_gui SET DEFAULT nextval('public.permessi_gui_id_gui_seq'::regclass);


--
-- Name: permessi_ruoli id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_ruoli ALTER COLUMN id SET DEFAULT nextval('public.permessi_ruoli_id_seq'::regclass);


--
-- Name: permessi_subfunzione id_subfunzione; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_subfunzione ALTER COLUMN id_subfunzione SET DEFAULT nextval('public.permessi_subfunzione_id_subfunzione_seq'::regclass);


--
-- Name: sca_storico_operazioni_utenti id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sca_storico_operazioni_utenti ALTER COLUMN id SET DEFAULT nextval('public.sirv_storico_operazioni_utenti_id_seq'::regclass);


--
-- Name: storico_cambio_password id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico_cambio_password ALTER COLUMN id SET DEFAULT nextval('public.storico_cambio_password_id_seq'::regclass);


--
-- Name: utenti id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utenti ALTER COLUMN id SET DEFAULT nextval('public.utenti_id_seq'::regclass);


--
-- Name: html_registrazioni id; Type: DEFAULT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.html_registrazioni ALTER COLUMN id SET DEFAULT nextval('spid.html_registrazioni_id_seq'::regclass);


--
-- Name: log_query_generate id; Type: DEFAULT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.log_query_generate ALTER COLUMN id SET DEFAULT nextval('spid.log_query_generate_id_seq'::regclass);


--
-- Name: sca_storico_login id; Type: DEFAULT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.sca_storico_login ALTER COLUMN id SET DEFAULT nextval('spid.sca_storico_login_id_seq'::regclass);


--
-- Name: spid_registrazioni id; Type: DEFAULT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.spid_registrazioni ALTER COLUMN id SET DEFAULT nextval('spid.spid_registrazioni_id_seq'::regclass);


--
-- Name: spid_registrazioni_esiti id; Type: DEFAULT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.spid_registrazioni_esiti ALTER COLUMN id SET DEFAULT nextval('spid.spid_registrazioni_esiti_id_seq'::regclass);


--
-- Name: spid_registrazioni_flag id; Type: DEFAULT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.spid_registrazioni_flag ALTER COLUMN id SET DEFAULT nextval('spid.spid_registrazioni_flag_id_seq'::regclass);


--
-- Name: spid_tipo_richiesta id; Type: DEFAULT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.spid_tipo_richiesta ALTER COLUMN id SET DEFAULT nextval('spid.spid_tipo_richiesta_id_seq'::regclass);


--
-- Name: spid_tipologia_utente_ruoli_endpoint id; Type: DEFAULT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.spid_tipologia_utente_ruoli_endpoint ALTER COLUMN id SET DEFAULT nextval('spid.spid_tipologia_utente_ruoli_endpoint_id_seq'::regclass);


--
-- Name: asl asl_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.asl
    ADD CONSTRAINT asl_pkey PRIMARY KEY (id);


--
-- Name: capability_permission capability_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.capability_permission
    ADD CONSTRAINT capability_permission_pkey PRIMARY KEY (capabilities_id, permissions_name);


--
-- Name: capability capability_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.capability
    ADD CONSTRAINT capability_pkey PRIMARY KEY (id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (name);


--
-- Name: category_secureobject category_secureobject_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category_secureobject
    ADD CONSTRAINT category_secureobject_pkey PRIMARY KEY (categories_name, secureobjects_name);


--
-- Name: comuni comune_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.comuni
    ADD CONSTRAINT comune_pk PRIMARY KEY (comune);


--
-- Name: extended_option ext_op_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.extended_option
    ADD CONSTRAINT ext_op_pk PRIMARY KEY (id);


--
-- Name: guc_endpoint_connector_config guc_endpoint_connector_config_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_endpoint_connector_config
    ADD CONSTRAINT guc_endpoint_connector_config_pkey PRIMARY KEY (id);


--
-- Name: guc_endpoint guc_endpoint_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_endpoint
    ADD CONSTRAINT guc_endpoint_pkey PRIMARY KEY (id);


--
-- Name: guc_operazioni guc_operazioni_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_operazioni
    ADD CONSTRAINT guc_operazioni_pkey PRIMARY KEY (id);


--
-- Name: guc_ruoli guc_ruoli_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_ruoli
    ADD CONSTRAINT guc_ruoli_pkey PRIMARY KEY (id);


--
-- Name: guc_utenti_ guc_utenti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_utenti_
    ADD CONSTRAINT guc_utenti_pkey PRIMARY KEY (id);


--
-- Name: guc_ruoli_appo id_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_ruoli_appo
    ADD CONSTRAINT id_pkey PRIMARY KEY (id);


--
-- Name: log_ruoli_utenti log_ruoli_utenti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_ruoli_utenti
    ADD CONSTRAINT log_ruoli_utenti_pkey PRIMARY KEY (id);


--
-- Name: log_utenti log_utenti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_utenti
    ADD CONSTRAINT log_utenti_pkey PRIMARY KEY (id);


--
-- Name: permessi_funzione permessi_funzione_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_funzione
    ADD CONSTRAINT permessi_funzione_nome_key UNIQUE (nome);


--
-- Name: permessi_funzione permessi_funzione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_funzione
    ADD CONSTRAINT permessi_funzione_pkey PRIMARY KEY (id_funzione);


--
-- Name: permessi_gui permessi_gui_id_subfunzione_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_gui
    ADD CONSTRAINT permessi_gui_id_subfunzione_nome_key UNIQUE (id_subfunzione, nome);


--
-- Name: permessi_gui permessi_gui_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_gui
    ADD CONSTRAINT permessi_gui_pkey PRIMARY KEY (id_gui);


--
-- Name: permessi_ruoli permessi_ruoli_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_ruoli
    ADD CONSTRAINT permessi_ruoli_nome_key UNIQUE (nome);


--
-- Name: permessi_ruoli permessi_ruoli_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_ruoli
    ADD CONSTRAINT permessi_ruoli_pkey PRIMARY KEY (id);


--
-- Name: permessi_subfunzione permessi_subfunzione_id_funzione_nome_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_subfunzione
    ADD CONSTRAINT permessi_subfunzione_id_funzione_nome_key UNIQUE (id_funzione, nome);


--
-- Name: permessi_subfunzione permessi_subfunzione_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_subfunzione
    ADD CONSTRAINT permessi_subfunzione_pkey PRIMARY KEY (id_subfunzione);


--
-- Name: permission_permission permission_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission_permission
    ADD CONSTRAINT permission_permission_pkey PRIMARY KEY (grouppermissions_name, simplepermissions_name);


--
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (name);


--
-- Name: sca_storico_operazioni_utenti pk_sirv; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.sca_storico_operazioni_utenti
    ADD CONSTRAINT pk_sirv PRIMARY KEY (id);


--
-- Name: secureobject secureobject_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.secureobject
    ADD CONSTRAINT secureobject_pkey PRIMARY KEY (name);


--
-- Name: storico_cambio_password storico_cambio_password_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.storico_cambio_password
    ADD CONSTRAINT storico_cambio_password_pk PRIMARY KEY (id);


--
-- Name: subject subject_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subject
    ADD CONSTRAINT subject_pkey PRIMARY KEY (name);


--
-- Name: guc_utenti_appo username_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_utenti_appo
    ADD CONSTRAINT username_pkey PRIMARY KEY (username);


--
-- Name: utenti utenti_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.utenti
    ADD CONSTRAINT utenti_pkey PRIMARY KEY (id);


--
-- Name: log_query_generate log_query_generate_pkey; Type: CONSTRAINT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.log_query_generate
    ADD CONSTRAINT log_query_generate_pkey PRIMARY KEY (id);


--
-- Name: spid_registrazioni_esiti spid_registrazioni_esiti_pkey; Type: CONSTRAINT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.spid_registrazioni_esiti
    ADD CONSTRAINT spid_registrazioni_esiti_pkey PRIMARY KEY (id);


--
-- Name: spid_registrazioni_flag spid_registrazioni_flag_pkey; Type: CONSTRAINT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.spid_registrazioni_flag
    ADD CONSTRAINT spid_registrazioni_flag_pkey PRIMARY KEY (id);


--
-- Name: spid_tipologia_utente_ruoli_endpoint spid_tipologia_utente_ruoli_endpoint_pkey; Type: CONSTRAINT; Schema: spid; Owner: postgres
--

ALTER TABLE ONLY spid.spid_tipologia_utente_ruoli_endpoint
    ADD CONSTRAINT spid_tipologia_utente_ruoli_endpoint_pkey PRIMARY KEY (id);


--
-- Name: ep_ruolo; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX ep_ruolo ON public.guc_ruoli USING btree (endpoint, id_utente, trashed) WHERE ((trashed IS NOT TRUE) AND ((endpoint)::text <> ALL ((ARRAY['Canina'::character varying, 'Felina'::character varying, 'CaninaReport'::character varying])::text[])));


--
-- Name: usernameloc; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX usernameloc ON public.guc_utenti_ USING btree (username, data_scadenza) WHERE enabled;


--
-- Name: guc_utenti guc_utenti_update; Type: RULE; Schema: public; Owner: postgres
--

CREATE RULE guc_utenti_update AS
    ON UPDATE TO public.guc_utenti DO INSTEAD  UPDATE public.guc_utenti_ SET password = new.password
  WHERE (guc_utenti_.id = new.id);


--
-- Name: guc_ruoli fk3f14cd7f287c01c6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_ruoli
    ADD CONSTRAINT fk3f14cd7f287c01c6 FOREIGN KEY (id_utente) REFERENCES public.guc_utenti_(id);


--
-- Name: capability fk481c01381a4a77c4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.capability
    ADD CONSTRAINT fk481c01381a4a77c4 FOREIGN KEY (category_name) REFERENCES public.category(name);


--
-- Name: capability fk481c013878da9e10; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.capability
    ADD CONSTRAINT fk481c013878da9e10 FOREIGN KEY (subject_name) REFERENCES public.subject(name);


--
-- Name: capability fk481c0138a63411e4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.capability
    ADD CONSTRAINT fk481c0138a63411e4 FOREIGN KEY (secureobject_name) REFERENCES public.secureobject(name);


--
-- Name: permission_permission fk58591a3f87ae77d3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission_permission
    ADD CONSTRAINT fk58591a3f87ae77d3 FOREIGN KEY (simplepermissions_name) REFERENCES public.permission(name);


--
-- Name: permission_permission fk58591a3f9632d8b9; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permission_permission
    ADD CONSTRAINT fk58591a3f9632d8b9 FOREIGN KEY (grouppermissions_name) REFERENCES public.permission(name);


--
-- Name: permessi_subfunzione fk7bc797ad6054ec5f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_subfunzione
    ADD CONSTRAINT fk7bc797ad6054ec5f FOREIGN KEY (id_funzione) REFERENCES public.permessi_funzione(id_funzione);


--
-- Name: category_secureobject fk7fdf1337829709fd; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category_secureobject
    ADD CONSTRAINT fk7fdf1337829709fd FOREIGN KEY (secureobjects_name) REFERENCES public.secureobject(name);


--
-- Name: category_secureobject fk7fdf133795a4f166; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category_secureobject
    ADD CONSTRAINT fk7fdf133795a4f166 FOREIGN KEY (categories_name) REFERENCES public.category(name);


--
-- Name: permessi_gui fk8f3f88f059ee70db; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.permessi_gui
    ADD CONSTRAINT fk8f3f88f059ee70db FOREIGN KEY (id_subfunzione) REFERENCES public.permessi_subfunzione(id_subfunzione);


--
-- Name: guc_utenti_ fka890d2474ec8574a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_utenti_
    ADD CONSTRAINT fka890d2474ec8574a FOREIGN KEY (asl_id) REFERENCES public.asl(id);


--
-- Name: guc_utenti_new fka890d2474ec8574a; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.guc_utenti_new
    ADD CONSTRAINT fka890d2474ec8574a FOREIGN KEY (asl_id) REFERENCES public.asl(id);


--
-- Name: capability_permission fkc7bb83561173ba76; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.capability_permission
    ADD CONSTRAINT fkc7bb83561173ba76 FOREIGN KEY (capabilities_id) REFERENCES public.capability(id);


--
-- Name: capability_permission fkc7bb8356ea720e4f; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.capability_permission
    ADD CONSTRAINT fkc7bb8356ea720e4f FOREIGN KEY (permissions_name) REFERENCES public.permission(name);


--
-- Name: log_utenti log_id_modificante; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_utenti
    ADD CONSTRAINT log_id_modificante FOREIGN KEY (id_modificante) REFERENCES public.utenti(id) ON UPDATE SET NULL;


--
-- Name: log_ruoli_utenti log_ruoli_utenti_fkey_log_utenti; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.log_ruoli_utenti
    ADD CONSTRAINT log_ruoli_utenti_fkey_log_utenti FOREIGN KEY (id_log_utente) REFERENCES public.log_utenti(id);


--
-- PostgreSQL database dump complete
--

