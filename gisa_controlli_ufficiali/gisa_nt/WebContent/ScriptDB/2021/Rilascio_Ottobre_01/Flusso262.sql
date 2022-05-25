
-- GISA
alter table access_dati ADD column  comune_apicoltore integer;
alter table access_dati ADD column  comune_trasportatore integer;
alter table access_dati ADD column  indirizzo_trasportatore text;
alter table access_dati ADD column  indirizzo_apicoltore text;
alter table access_dati ADD column  cap_trasportatore text;
alter table access_dati ADD column  cap_apicoltore text;
alter table access_dati ADD column  tipo_attivita_apicoltore text;
--alter table contact_ add column telefono text;

CREATE OR REPLACE FUNCTION public.dbi_check_last_login(
    usr character varying,
    timeout timestamp with time zone)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;   
n_record int;
BEGIN
	-- DA ELIMINAREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE
	n_record := (select count(*) from access a where a.username = usr and role_id > 0 and a.trashed_date is null and a.enabled);
	IF (n_record = 0) THEN
		msg='KO_NON_ESISTE';
	ELSE
		tot := (select count(*) from access a where a.username = usr and a.trashed_date is null and last_login >= timeout);

		IF (tot=0) THEN
			msg='KO';
		ELSE 	
			msg='OK';
		END IF;
	END IF;
	--------------------------------------------
	RETURN 'OK';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_check_last_login(character varying, timestamp with time zone)
  OWNER TO postgres;

  -- Function: public.dbi_check_last_login_ext(character varying, timestamp with time zone)

-- DROP FUNCTION public.dbi_check_last_login_ext(character varying, timestamp with time zone);

CREATE OR REPLACE FUNCTION public.dbi_check_last_login_ext(
    usr character varying,
    timeout timestamp with time zone)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;   
n_record int;
BEGIN
	n_record := (select count(*) from access_ext a where a.username = usr and role_id > 0 and a.trashed_date is null and a.enabled);
	IF (n_record = 0) THEN
		msg='KO_NON_ESISTE';
	ELSE
		tot := (select count(*) from access_ext a where a.username = usr and a.trashed_date is null and last_login >= timeout);

		IF (tot=0) THEN
			msg='KO';
		ELSE 	
			msg='OK';
		END IF;
	END IF;
	RETURN 'OK';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_check_last_login_ext(character varying, timestamp with time zone)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.dbi_insert_utente_ext(
    usr character varying,
    password character varying,
    role_id integer,
    enteredby integer,
    modifiedby integer,
    enabled boolean,
    site_id integer,
    namefirst_input character varying,
    namelast_input character varying,
    cf character varying,
    notes text,
    luogo text,
    nickname character varying,
    email character varying,
    expires timestamp with time zone,
    inaccess text,
    inni text,
    numreg text,
    input_gestore_acque integer,
    input_piva text,
    input_tipo_attivita_apicoltore text,
    input_comune_apicoltore integer,
    input_indirizzo_apicoltore text,
    input_cap_indirizzo_apicoltore text,
    input_comune_trasportatore integer,
    input_indirizzo_trasportatore text,
    input_cap_indirizzo_trasportatore text,
    input_telefono text)
  RETURNS text AS
$BODY$
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
		VALUES (  us_id, site_id, (case when input_piva <> '' then input_piva else cf end), numreg,
		input_tipo_attivita_apicoltore,input_comune_apicoltore ,input_indirizzo_apicoltore,input_cap_indirizzo_apicoltore,
		input_comune_trasportatore,input_indirizzo_trasportatore, input_cap_indirizzo_trasportatore); 

		if(input_gestore_acque>0) then
                   insert into users_to_gestori_acque(id_gestore_acque_anag, user_id) values(input_gestore_acque, us_id); 
		end if;


	
	msg = COALESCE(msg, 'OK');
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_insert_utente_ext(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, text, text, text, integer, text, text, integer, text, text, integer, text, text, text)
  OWNER TO postgres;


  
CREATE OR REPLACE FUNCTION public.dbi_insert_utente(
    usr character varying,
    password character varying,
    role_id integer,
    enteredby integer,
    modifiedby integer,
    enabled boolean,
    site_id integer,
    namefirst_input character varying,
    namelast_input character varying,
    cf character varying,
    notes text,
    luogo text,
    nickname character varying,
    email character varying,
    expires timestamp with time zone,
    id_struttura integer,
    inaccess text,
    indpat text,
    inni text,
    input_telefono text)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
con_id int;
us_id2 int ;
flag boolean;
delegavisibilita text ;

   
BEGIN
	
	namefirst_input:= trim(namefirst_input);
namelast_input:= trim(namelast_input);
cf:= trim(cf);

	IF (role_id=-1) THEN
		enabled:=false;
	ELSE
		enabled:=true;
	END IF;

	IF (id_struttura=-1) THEN
		id_struttura:=null;
	END IF;

	con_id := (select contact_id from contact_ c where c.namefirst ilike namefirst_input and c.namelast ilike namelast_input and c.codice_fiscale ilike cf and c.trashed_date is null limit 1);
	
	IF (con_id is null) THEN	
		con_id:=nextVal('contact_contact_id_seq');
		
		INSERT INTO contact_ ( contact_id, namefirst, namelast, enteredby, modifiedby, site_id, codice_fiscale, notes, enabled,luogo,nickname,visibilita_delega, duns_number ) 
		VALUES ( con_id, upper(namefirst_input), upper(namelast_input), 964, 964, site_id, cf, notes, enabled,luogo,nickname,delegavisibilita, input_telefono );

			
		--con_id=currVal('contact_contact_id_seq');
		INSERT INTO contact_emailaddress(contact_id, emailaddress_type, email, enteredby, modifiedby, primary_email)
		VALUES (con_id, 1, email, 964, 964, true);
	end if;
	
	
		us_id=nextVal('access_user_id_seq');
	
if (site_id>0)
		then
			delegavisibilita:=(select description from lookup_site_id where code =site_id);
			else
			delegavisibilita:=cf;
			end if ;

		 
		INSERT INTO access_ ( user_id, username, password, contact_id, role_id, enteredby, modifiedby, timezone, currency, language, enabled, expires, in_access, in_dpat, in_nucleo_ispettivo) 
		VALUES (  us_id, usr, password, con_id, role_id, 964, 964, 'Europe/Berlin', 'EUR', 'it_IT', enabled, expires::timestamp without time zone, inaccess::boolean, indpat::boolean, inni::boolean); 

		INSERT INTO access_dati ( user_id, site_id, visibilita_delega) 
		VALUES (  us_id, site_id, delegavisibilita ); 


	IF (id_struttura is not null) THEN
		us_id2=nextVal('access_collegamento_id_seq');
		INSERT INTO access_collegamento (id,id_utente,id_collegato,enabled) 
		VALUES (us_id2,us_id,id_canile,enabled); 
	END IF;

	msg = COALESCE(msg, 'OK');
	
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, text, text, text,text)
  OWNER TO postgres;

-- BDU
--DROP FUNCTION public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, integer, integer, text, integer);

-- Function: public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, integer, integer, text)

-- DROP FUNCTION public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, integer, integer, text);
alter table contact_ add column telefono text;


CREATE OR REPLACE FUNCTION public.dbi_check_last_login(
    usr character varying,
    timeout timestamp with time zone)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;   
n_record int;
BEGIN
 ------------------------ DA ELIMINARE ----------------------------------------
	n_record := (select count(*) from access a where a.username = usr and role_id > 0 and a.trashed_date is null and a.enabled);
	IF (n_record = 0) THEN
		msg='KO_NON_ESISTE';
	ELSE
		tot := (select count(*) from access a where a.username = usr and a.trashed_date is null and last_login >= timeout);

		IF (tot=0) THEN
			msg='KO';
		ELSE 	
			msg='OK';
		END IF;
	END IF;
	----------------------------------------------------------------------------
	RETURN 'OK';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_check_last_login(character varying, timestamp with time zone)
  OWNER TO postgres;


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

	us_id := (select user_id from access_ a where a.username = usr and a.trashed_date is null);
	IF (us_id is null) THEN	
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
	END IF;

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
alter table guc_utenti_ ADD column  comune_apicoltore integer;
alter table guc_utenti_ ADD column  comune_trasportatore integer;
alter table guc_utenti_ ADD column  indirizzo_trasportatore text;
alter table guc_utenti_ ADD column  indirizzo_apicoltore text;
alter table guc_utenti_ ADD column  cap_trasportatore text;
alter table guc_utenti_ ADD column  cap_apicoltore text;
alter table guc_utenti_ ADD column  tipo_attivita_apicoltore text;

-- Function: public.dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text, integer, integer, text, text, integer, text, text, integer, text, text)

-- DROP FUNCTION public.dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text, integer, integer, text, text, integer, text, text, integer, text, text);

CREATE OR REPLACE FUNCTION public.dbi_insert_utente_guc(
    input_cf text,
    input_cognome text,
    input_email text,
    input_enabled boolean,
    input_enteredby integer,
    input_expires timestamp without time zone,
    input_modifiedby integer,
    input_note text,
    input_nome text,
    input_password text,
    input_username text,
    input_asl_id integer,
    input_password_encrypted text,
    input_canile_id integer,
    input_canile_description text,
    input_luogo text,
    input_num_autorizzazione text,
    input_id_importatore integer,
    input_canilebdu_id integer,
    input_canilebdu_description text,
    input_importatori_description text,
    input_id_provincia_iscrizione_albo_vet_privato integer,
    input_nr_iscrione_albo_vet_privato text,
    input_id_utente_guc_old integer,
    input_suap_ip_address text,
    input_suap_istat_comune text,
    input_suap_pec text,
    input_suap_callback text,
    input_suap_shared_key text,
    input_suap_callback_ko text,
    input_num_registrazione_stab text,
    input_suap_livello_accreditamento integer,
    input_suap_descrizione_livello_accreditamento text,
    input_telefono text,
    input_ruolo_id_gisa integer,
    input_ruolo_descrizione_gisa text,
    input_ruolo_id_gisa_ext integer,
    input_ruolo_descrizione_gisa_ext text,
    input_ruolo_id_bdu integer,
    input_ruolo_descrizione_bdu text,
    input_ruolo_id_vam integer,
    input_ruolo_descrizione_vam text,
    input_ruolo_id_importatori integer,
    input_ruolo_descrizione_importatori text,
    input_ruolo_id_digemon integer,
    input_ruolo_descrizione_digemon text,
    input_luogo_vam text,
    input_id_provincia_vam integer,
    input_nr_iscrizione_vam text,
    input_gestore_acque integer,
    input_comune_gestore_acque integer,
    input_piva text,
    input_tipo_attivita_apicoltore text,
    input_comune_apicoltore integer,
    input_indirizzo_apicoltore text,
    input_cap_indirizzo_apicoltore text,
    input_comune_trasportatore integer,
    input_indirizzo_trasportatore text,
    input_cap_indirizzo_trasportatore text)
  RETURNS text AS
$BODY$
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

IF (input_username is null or input_username = '') THEN
select concat('spid_usr_',us_id) into input_username;
END IF;

IF (input_password  is null or input_password  = '') THEN
select md5(concat('spid_pwd_',us_id)) into input_password ;
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
	RETURN concat(msg,';;',us_id,';;','spid_usr_',us_id);
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text, integer, integer, text, text, integer, text, text, integer, text, text)
  OWNER TO postgres;



update  guc_endpoint_connector_config set sql= 'select * from dbi_insert_utente_guc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?,?);' where id=62;


-- View: public.guc_utenti

-- DROP VIEW public.guc_utenti;
CREATE OR REPLACE VIEW public.guc_utenti AS 
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
                   FROM guc_utenti_
                  WHERE guc_utenti_.data_scadenza > 'now'::text::date::text::date OR guc_utenti_.data_scadenza IS NULL AND guc_utenti_.enabled) a
          ORDER BY a.username, a.data_scadenza) aa;

ALTER TABLE public.guc_utenti
  OWNER TO postgres;

-- Rule: guc_utenti_update ON public.guc_utenti

-- DROP RULE guc_utenti_update ON public.guc_utenti;

CREATE OR REPLACE RULE guc_utenti_update AS
    ON UPDATE TO guc_utenti DO INSTEAD  UPDATE guc_utenti_ SET password = new.password
  WHERE guc_utenti_.id = new.id;



-- DIGEMON
alter table contact_ add column telefono text;
-- Function: public.dbi_check_last_login(character varying, timestamp with time zone)

-- DROP FUNCTION public.dbi_check_last_login(character varying, timestamp with time zone);

CREATE OR REPLACE FUNCTION public.dbi_check_last_login(
    usr character varying,
    timeout timestamp with time zone)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;   
n_record int;
BEGIN
	-------------------------------------- DA ELIMINARE ------------------------------------------------------------------------
	n_record := (select count(*) from access a where a.username = usr and role_id > 0 and a.trashed_date is null and a.enabled);
	IF (n_record = 0) THEN
		msg='KO_NON_ESISTE';
	ELSE
		tot := (select count(*) from access a where a.username = usr and a.trashed_date is null and last_login >= timeout);

		IF (tot=0) THEN
			msg='KO';
		ELSE 	
			msg='OK';
		END IF;
	END IF;
	----------------------------------------------------------------------------------------------------------------------------
	RETURN 'OK';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_check_last_login(character varying, timestamp with time zone)
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.dbi_insert_utente(
    character varying,
    character varying,
    integer,
    integer,
    integer,
    boolean,
    integer,
    character varying,
    character varying,
    character varying,
    text,
    text,
    character varying,
    character varying,
    timestamp with time zone,
    text)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
con_id int;
us_id2 int ;

usr1 character varying := $1;
password1 character varying := $2;
role_id1 integer:= $3;
enteredby1 integer := $4;
modifiedby1 integer := $5;
enabled1  boolean := $6;
site_id1 integer := $7;
namefirst1 character varying := $8;
namelast1 character varying := $9;
cf1 character varying:= $10;
notes1 text := $11;
luogo1 text := $12;
nickname1 character varying := $13;
email1 character varying := $14;
expires1  timestamp with time zone := $15;
input_telefono text := $16;


BEGIN

	IF (role_id1=-1) THEN
		enabled1:=false;
	ELSE
		enabled1:=true;
	END IF;

	us_id := (select user_id from access a where a.username = usr1 and a.trashed_date is null);
	IF (us_id is null) THEN	
		us_id=nextVal('access_user_id_seq');
		con_id=nextVal('contact_contact_id_seq');
		INSERT INTO access ( user_id, username, password, contact_id, site_id, role_id, enteredby, modifiedby, timezone, currency, language, enabled, expires) 
		VALUES (  us_id, usr1, password1, con_id, site_id1, role_id1, 964, 964, 'Europe/Berlin', 'EUR', 'it_IT', enabled1, expires1::timestamp without time zone); 

		--con_id=currVal('contact_contact_id_seq');
		--us_id=currVal('access_user_id_seq');
		INSERT INTO contact ( contact_id, user_id, namefirst, namelast, enteredby, modifiedby, site_id, codice_fiscale, notes, enabled,luogo,nickname, telefono ) 
		VALUES ( con_id, us_id, namefirst1, namelast1, 964, 964, site_id1, cf1, notes1, enabled1,luogo1,nickname1, input_telefono );
			
		--con_id=currVal('contact_contact_id_seq');
		INSERT INTO contact_emailaddress(contact_id, emailaddress_type, email, enteredby, modifiedby, primary_email)
		VALUES (con_id, 1, email1, 964, 964, true);
	END IF;
	
	msg = 'OK';
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
  
--VAM
-- Function: public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, text, integer, text)

-- DROP FUNCTION public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer, text, integer, text);
-- Function: public.dbi_check_last_login(character varying, timestamp with time zone)

-- DROP FUNCTION public.dbi_check_last_login(character varying, timestamp with time zone);

CREATE OR REPLACE FUNCTION public.dbi_check_last_login(
    usr character varying,
    timeout timestamp with time zone)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;   
n_record int;
BEGIN
----------------------------- DA ELIMINARE -------------------------------------------------------------------------------
	n_record := (select count(*) from utenti_super a where a.username = usr and a.trashed_date is null and a.enabled);
	IF (n_record = 0) THEN
		msg='KO_NON_ESISTE';
	ELSE
		tot := (select count(*) from utenti_super a where a.username = usr and a.trashed_date is null and a.enabled and (a.last_login >= timeout or a.last_login is null));

		IF (tot=0) THEN
			msg='KO';
		ELSE 	
			msg='OK';
		END IF;
	END IF;
	----------------------------------------------------------------------------------------------
	RETURN 'OK';
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_check_last_login(character varying, timestamp with time zone)
  OWNER TO postgres;


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
    clinica integer,
    luogo_vam text,
    id_provincia integer,
    nr_iscrizione text,
    input_telefono text)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
us_id2 int ;
t timestamp without time zone;
asl_val int;
provincia text;
cat_name text;
BEGIN

	IF (site_id=-1) THEN
	   asl_val=null;
	ELSE
	   asl_val=site_id;
	END IF;

	IF (role_id=-1) THEN
		enabled:=false;
	ELSE
		enabled:=true;
	END IF;

	IF (clinica=-1) THEN
		clinica:=null;
	END IF;

	t=now();

	--Gestione Province
        provincia:='';
	IF (id_provincia=61) THEN
	   provincia:='CE';
	ELSIF(id_provincia=62) THEN
	   provincia:='BN';
	ELSIF(id_provincia=63) THEN
	   provincia:='NA';
	ELSIF(id_provincia=64) THEN
	   provincia:='AV';
	ELSIF(id_provincia=65) THEN
	   provincia:='SA';
	END IF;
	--Fine Gestione Province

	
	us_id := (select us.id from utenti_super us where us.username = usr and us.trashed_date is null);
	IF (us_id is null) THEN	
		us_id=nextVal('utenti_super_id_seq');
		INSERT INTO utenti_super (id, data_scadenza,enabled,entered,modified,note,password,username,num_iscrizione_albo, sigla_provincia,luogo) 
		VALUES (us_id,expires::timestamp without time zone,enabled,t,t,notes,password,usr,nr_iscrizione,provincia,luogo_vam);
	END IF;

	IF (clinica is not null) THEN
		us_id2=nextVal('utenti_id_seq');
		INSERT INTO utenti (id,codice_fiscale,cognome,data_scadenza,email,enabled,entered,nome,note,password,ruolo,username,clinica,superutente,asl_referenza,telefono1) 
		VALUES (us_id2,cf,namelast,expires::timestamp without time zone,email,enabled,t,namefirst,notes,password,role_id,usr,clinica,us_id,asl_val,input_telefono); 

		INSERT INTO secureobject (name) VALUES (us_id2::text);
		
		cat_name := (select nome from permessi_ruoli where id=role_id);
		INSERT INTO category_secureobject (categories_name,secureobjects_name) 
		VALUES (cat_name,us_id2::text);
	END IF;
	
	msg = 'OK';
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;



  -- aggiornamento endpoint  per inserimento
update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);' where id_endpoint = 7 and id_operazione=2; -- endpoint digemon 
update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);' where id_endpoint = 1 and id_operazione=2;; -- endpoint bdu 
update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);' where id_endpoint = 2 and id_operazione=2;; -- endpoint gisa
update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);' where id_endpoint = 4 and id_operazione=2;; -- endpoint vam
update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente_ext(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);' where id_endpoint = 3 and id_operazione=2;; -- endpoint ext gisa