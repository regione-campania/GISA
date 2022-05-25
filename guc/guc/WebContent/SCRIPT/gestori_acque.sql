--GUC
  INSERT INTO guc_operazioni(id, nome, enabled) VALUES (23, 'GetGestoriAcque', true);
  INSERT INTO guc_operazioni(id, nome, enabled) VALUES (24, 'GetComuni', true);
  INSERT INTO guc_endpoint_connector_config(id,id_operazione, id_endpoint, sql, enabled)
    VALUES (nextval('guc_endpoint_connector_config_id_seq'),23, 3, 'select * from public_functions.dbi_get_gestori_acque(?);', true);
  INSERT INTO guc_endpoint_connector_config(id,id_operazione, id_endpoint, sql, enabled)
    VALUES (nextval('guc_endpoint_connector_config_id_seq'),24, 3, 'select * from public_functions.dbi_get_comuni(?) where cod_regione = 15;', true);

  alter table guc_utenti_ add column gestore_acque integer;
  alter table guc_utenti_ add column comune_gestore_acque integer;
  update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente_guc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?,?);' 
where id_operazione = 2 and id_endpoint = 6;
update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente_ext(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);' 
where id_operazione = 2 and id_endpoint = 3;
update guc_endpoint_connector_config set sql = 'select * from dbi_cambia_profilo_utente_ext(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);' 
where id_operazione = 3 and id_endpoint = 3;
update guc_endpoint_connector_config set sql = 'select * from dbi_update_anagrafica_utente_ext(?,?,?,?,?,?,?,?,?,?,?);' 
where id_operazione = 5 and id_endpoint = 3;

-- DROP FUNCTION dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text);
CREATE OR REPLACE FUNCTION dbi_insert_utente_guc(
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
    input_comune_gestore_acque integer)
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text, text, integer, text, integer,integer)
  OWNER TO postgres;


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
    aa.comune_gestore_acque
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
            a.comune_gestore_acque
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
                    guc_utenti_.comune_gestore_acque
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


  
--GISA
alter table gestori_acque_gestori add column enabled boolean default true;
alter table gestori_acque_gestori add column note_hd text;

CREATE OR REPLACE FUNCTION public_functions.dbi_get_gestori_acque(IN id_to_search integer)
  RETURNS TABLE(id integer, nome character varying) AS
$BODY$
BEGIN

		FOR id, nome
		in
		select g.id ,g.nome
		from 
		gestori_acque_gestori g
		where enabled and (id_to_search=-1 or id_to_search = g.id)
		order by g.nome		
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_gestori_acque(integer)
  OWNER TO postgres;

--drop function  public_functions.dbi_get_comuni(IN id_to_search integer)
  CREATE OR REPLACE FUNCTION public_functions.dbi_get_comuni(IN id_to_search integer)
  RETURNS TABLE(id integer, nome character varying, cod_regione integer) AS
$BODY$
BEGIN
		FOR id, nome, cod_regione
		in
		select c.id ,c.nome, c.cod_regione
		from 
		comuni1 c
		where id_to_search=-1 or id_to_search = c.id
		order by c.nome		
    LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.dbi_get_comuni(integer)
  OWNER TO postgres;


-- DROP FUNCTION dbi_cambia_profilo_utente_ext(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, text, text, date, boolean, text);
CREATE OR REPLACE FUNCTION dbi_cambia_profilo_utente_ext(
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
    inaccess text,
    inni text,
    datascadenza date,
    flagcessato boolean,
    numregstab text,
    input_gestore_acque integer )
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
con_id int;
us_id2 int ;
flag boolean;

   
BEGIN

 con_id := (select contact_id from access_ext where username = usr);

	       update access_ext_ set data_scadenza = datascadenza where username = usr and data_scadenza is null and trashed_date is null ;

	       if (flagcessato=false)
		then	       
		us_id=nextVal('access_user_id_ext_seq');
		con_id=nextVal('contact_ext_contact_id_seq');
		INSERT INTO access_ext_ ( user_id, username, password, contact_id, site_id, role_id, enteredby, modifiedby, timezone, currency, language, enabled, expires,in_access, in_nucleo_ispettivo) 
		VALUES (  us_id, usr, password, con_id, site_id, role_id, 964, 964, 'Europe/Berlin', 'EUR', 'it_IT', enabled, expires::timestamp without time zone, inaccess::boolean, inni::boolean); 

INSERT INTO access_dati ( user_id, site_id, visibilita_delega) 
		VALUES (  us_id, site_id, delegavisibilita ); 

                if(us_id>0) then
                   insert into users_to_gestori_acque(id_gestore_acque_anag, user_id) values(input_gestore_acquere, us_id); 
		end if;
		
		end if;
	
	msg = 'OK';
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_cambia_profilo_utente_ext(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, text, text, date, boolean, text,integer)
  OWNER TO postgres;


--DROP FUNCTION dbi_insert_utente_ext(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, text, text, text, integer);
CREATE OR REPLACE FUNCTION dbi_insert_utente_ext(
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
    input_gestore_acque integer)
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


con_id := (select contact_id from contact_ext c where c.namefirst ilike namefirst_input and c.namelast ilike namelast_input and c.codice_fiscale ilike cf and c.trashed_date is null limit 1);
	IF (con_id is null) THEN
			con_id=nextVal('contact_ext_contact_id_seq');

--con_id=currVal('contact_ext_contact_id_seq');
		--us_id=currVal('access_user_id_ext_seq');
		INSERT INTO contact_ext ( contact_id, namefirst, namelast, enteredby, modifiedby, codice_fiscale, notes, enabled,luogo,nickname) 
		VALUES ( con_id, upper(namefirst_input),  upper(namelast_input), 964, 964, cf, notes, enabled,luogo,nickname );
			 
		--con_id=currVal('contact_ext_contact_id_seq');
		INSERT INTO contact_emailaddress_ext(contact_id, emailaddress_type, email, enteredby, modifiedby, primary_email)
		VALUES (con_id, 1, email, 964, 964, true);

		end if;
	

	
		us_id=nextVal('access_user_id_ext_seq');
		INSERT INTO access_ext ( user_id, username, password, contact_id, role_id, enteredby, modifiedby, timezone, currency, language, enabled, expires,in_access, in_nucleo_ispettivo) 
		VALUES (  us_id, usr, password, con_id, role_id, 964, 964, 'Europe/Berlin', 'EUR', 'it_IT', enabled, expires::timestamp without time zone, inaccess::boolean, inni::boolean); 

		INSERT INTO access_dati ( user_id, site_id, visibilita_delega, num_registrazione_stab) 
		VALUES (  us_id, site_id, cf, numreg ); 

		if(us_id>0) then
                   insert into users_to_gestori_acque(id_gestore_acque_anag, user_id) values(input_gestore_acque, us_id); 
		end if;


	
	msg = COALESCE(msg, 'OK');
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_insert_utente_ext(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, text, text, text, integer)
  OWNER TO postgres;



  -- Function: dbi_update_anagrafica_utente_ext(character varying, character varying, character varying, character varying, character varying, character varying, text, character varying, character varying, character varying)

-- DROP FUNCTION dbi_update_anagrafica_utente_ext(character varying, character varying, character varying, character varying, character varying, character varying, text, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION dbi_update_anagrafica_utente_ext(
    password_new character varying,
    username_new character varying,
    old_username character varying,
    namefirst_new character varying,
    namelast_new character varying,
    cf character varying,
    notes_new text,
    email_new character varying,
    luogo_new character varying,
    nickname_new character varying,
    input_gestore_acque integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
mod timestamp without time zone;
us_id int;
us_id2 int;
in_d boolean;
in_n boolean;
   
BEGIN

	
raise info 'datiutente %  ', old_username;
	mod = now();
	UPDATE access_ext_ a set password=password_new, username = username_new ,modified = mod, modifiedby = 964
	WHERE a.username = old_username and a.trashed_date is null;

	UPDATE contact_ext_ set namefirst = namefirst_new,visibilita_delega=cf, namelast = namelast_new, modified = mod, modifiedby = 964, codice_fiscale = cf, notes = notes_new,
	  luogo=luogo_new ,nickname=nickname_new
	WHERE contact_id in ( select contact_id from access_ext_ a where a.username = username_new and a.trashed_date is null);

	UPDATE contact_emailaddress_ext set email = email_new, modified = mod, modifiedby = 964 
	WHERE contact_id in ( select contact_id from access_ext_ a where a.username = username_new and a.trashed_date is null);


--	in_d := (select r.in_dpat from role r where r.enabled and r.role_id = role_id_new); 		--in_dpat
--	in_n := (select r.in_nucleo_ispettivo from role r where r.enabled and r.role_id = role_id_new); --in_nucleo_ispettivo	
--	IF (in_d is false AND in_n is true) THEN 
--		us_id := (select a.user_id from access a where a.username = username_new);  		--id di access
--		UPDATE anagrafica_nominativo set trashed=true where user_id_access = us_id;
--		INSERT INTO anagrafica_nominativo (id_asl,nome,cognome,cf,mail,disabled,user_id_access,id_ruolo) 
--		VALUES (site_id_new,namefirst_new,namelast_new,cf,email_new,true,us_id,role_id_new);
--	ELSE
--		us_id := (select a.user_id from access a where a.username = username_new);
--		UPDATE anagrafica_nominativo set trashed=true where user_id_access = us_id;
--	END IF;

	msg = 'OK';
	RETURN msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_update_anagrafica_utente_ext(character varying, character varying, character varying, character varying, character varying, character varying, text, character varying, character varying, character varying,integer)
  OWNER TO postgres;




  


  


