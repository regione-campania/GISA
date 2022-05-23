CREATE OR REPLACE FUNCTION dbi_get_ruoli_utente()
  RETURNS TABLE(id_ruolo integer, descrizione_ruolo text, note_ruolo text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR id_ruolo, descrizione_ruolo, note_ruolo
in
select rp.id,rp.nome,rp.descrizione from permessi_ruoli  rp join permessi_ruoli_abilitati ra on  rp.id=ra.id_ruolo where ra.enabled is true  order by rp.nome
LOOP
        RETURN NEXT;
END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION dbi_get_ruoli_utente()
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION dbi_get_cliniche_utente()
  RETURNS TABLE(asl_id integer, id_clinica integer, nome_clinica text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR asl_id, id_clinica, nome_clinica
in
select asl, id, nome from clinica  where trashed_date is null order by asl,nome
LOOP
        RETURN NEXT;
END LOOP;
     RETURN;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION dbi_get_cliniche_utente()
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION dbi_insert_utente(usr character varying, password character varying, role_id integer, enteredby integer, modifiedby integer, enabled boolean, site_id integer, namefirst character varying, namelast character varying, cf character varying, notes text, luogo text, nickname character varying, email character varying, expires timestamp with time zone,clinica integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
us_id2 int ;
t timestamp without time zone;
asl_val int;
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
	us_id := (select us.id from utenti_super us where us.username = usr and us.trashed_date is null);
	IF (us_id is null) THEN	
		us_id=nextVal('utenti_super_id_seq');
		INSERT INTO utenti_super (id, data_scadenza,enabled,entered,modified,note,password,username) 
		VALUES (us_id,expires::timestamp without time zone,enabled,t,t,notes,password,usr);
	END IF;

	IF (clinica is not null) THEN
		us_id2=nextVal('utenti_id_seq');
		INSERT INTO utenti (id,codice_fiscale,cognome,data_scadenza,email,enabled,entered,nome,note,password,ruolo,username,clinica,superutente,asl_referenza) 
		VALUES (us_id2,cf,namelast,expires::timestamp without time zone,email,enabled,t,namefirst,notes,password,role_id,usr,clinica,us_id,asl_val); 

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
ALTER FUNCTION dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone,integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION dbi_update_utente(password_new character varying,username_new character varying, site_id_new integer, role_id_new integer, enabled_new boolean, expires_new timestamp with time zone, old_username character varying, namefirst_new character varying, namelast_new character varying, cf character varying, notes_new text, luogo_new text, nickname_new character varying, email_new character varying)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
mod timestamp without time zone;
asl_val int;
cat_name text;
us_id int;   
BEGIN

	IF (site_id_new=-1) THEN
	   asl_val=null;
	ELSE
	   asl_val=site_id_new;
	END IF;

	IF (role_id_new=-1) THEN
		enabled_new:=false;
	ELSE
		enabled_new:=true;
		cat_name := (select nome from permessi_ruoli where id=role_id_new);
		UPDATE category_secureobject set categories_name=cat_name where secureobjects_name in (select id::text from utenti where username=old_username and trashed_date is null);
	END IF;

	mod = now();
	UPDATE utenti_super set password=password_new, username = username_new,data_scadenza=expires_new::timestamp without time zone,enabled=enabled_new,modified=mod,note=notes_new,modified_by=964 
	WHERE username = old_username and trashed_date is null;

	UPDATE utenti set password=password_new, codice_fiscale=cf,cognome=namelast_new,data_scadenza=expires_new::timestamp without time zone,email=email_new,enabled=enabled_new,nome=namefirst_new,note=notes_new,ruolo=role_id_new,username=username_new,asl_referenza=asl_val,modified_by=964
	WHERE username = old_username;
	
	msg = 'OK';
	RETURN msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_update_utente(character varying,character varying, integer, integer, boolean, timestamp with time zone, character varying, character varying, character varying, character varying, text, text, character varying, character varying)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION dbi_disable_utente(usr character varying)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
mod timestamp without time zone;
   
BEGIN
	mod = now();
	UPDATE utenti SET modified = mod, modified_by = 964, enabled = false WHERE username = usr;
	msg = 'OK';
	RETURN msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_disable_utente(character varying)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION dbi_enable_utente(usr character varying, clinica_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
mod timestamp without time zone;
   
BEGIN
	mod = now();
	UPDATE utenti SET modified = mod, modified_by = 964, enabled = true WHERE username = usr and clinica=clinica_id;
	msg = 'OK';
	RETURN msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_enable_utente(character varying, integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION dbi_check_esistenza_utente(usr character varying)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;   
BEGIN
	tot := (select count(*) from utenti_super us where us.username = usr and us.trashed_date is null);
	IF (tot=0) THEN
		msg='KO';
	ELSE 	
		msg='OK';
	END IF;
	RETURN msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_check_esistenza_utente(character varying)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION dbi_check_esistenza_utente_by_struttura(usr character varying,clinica_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;   
BEGIN
	tot := (select count(*) from utenti u where u.username = usr and u.clinica=clinica_id);
	IF (tot=0) THEN
		msg='KO';
	ELSE 	
		msg='OK';
	END IF;
	RETURN msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION   dbi_check_esistenza_utente_by_struttura(character varying,integer)
  OWNER TO postgres;
