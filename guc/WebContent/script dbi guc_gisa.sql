CREATE OR REPLACE FUNCTION dbi_get_ruoli_utente()
  RETURNS TABLE(id_ruolo integer, descrizione_ruolo text, note_ruolo text) AS
$BODY$
DECLARE
r RECORD;	
BEGIN
FOR id_ruolo, descrizione_ruolo, note_ruolo
in
select role_id, role , '' from role where enabled = true order by role    
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


CREATE OR REPLACE FUNCTION dbi_insert_utente(usr character varying, password character varying, role_id integer, enteredby integer, modifiedby integer, enabled boolean, site_id integer, namefirst character varying, namelast character varying, cf character varying, notes text, luogo text, nickname character varying, email character varying, expires timestamp with time zone, id_struttura integer)
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

	IF (id_struttura=-1) THEN
		id_struttura:=null;
	END IF;

	us_id := (select user_id from access a where a.username = usr and a.trashed_date is null);
	IF (us_id is null) THEN	
		us_id=nextVal('access_user_id_seq');
		con_id=nextVal('contact_contact_id_seq');
		INSERT INTO access ( user_id, username, password, contact_id, site_id, role_id, enteredby, modifiedby, timezone, currency, language, enabled, expires) 
		VALUES (  us_id, usr, password, con_id, site_id, role_id, 964, 964, 'Europe/Berlin', 'EUR', 'it_IT', enabled, expires::timestamp without time zone); 

		con_id=currVal('contact_contact_id_seq');
		us_id=currVal('access_user_id_seq');
		INSERT INTO contact ( contact_id, user_id, namefirst, namelast, enteredby, modifiedby, site_id, codice_fiscale, notes, enabled,luogo,nickname ) 
		VALUES ( con_id, us_id, namefirst, namelast, 964, 964, site_id, cf, notes, enabled,luogo,nickname );
			
		con_id=currVal('contact_contact_id_seq');
		INSERT INTO contact_emailaddress(contact_id, emailaddress_type, email, enteredby, modifiedby, primary_email)
		VALUES (con_id, 1, email, 964, 964, true);
	END IF;

	IF (id_struttura is not null) THEN
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
ALTER FUNCTION dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION dbi_update_utente(password_new character varying,username_new character varying, site_id_new integer, role_id_new integer, enabled_new boolean, expires_new timestamp with time zone, old_username character varying, namefirst_new character varying, namelast_new character varying, cf character varying, notes_new text, luogo_new text, nickname_new character varying, email_new character varying)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
mod timestamp without time zone;
   
BEGIN

	IF (role_id_new=-1) THEN
		enabled_new:=false;
	ELSE
		enabled_new:=true;
	END IF;

	mod = now();
	UPDATE access a set password=password_new, username = username_new, site_id = site_id_new, role_id = role_id_new, modified = mod, modifiedby = 964, enabled = enabled_new, expires = expires_new::timestamp without time zone
	WHERE a.username = old_username and a.trashed_date is null;

	UPDATE contact set namefirst = namefirst_new, namelast = namelast_new, modified = mod, modifiedby = 964, site_id = site_id_new, codice_fiscale = cf, notes = notes_new, enabled = enabled_new , luogo=luogo_new ,nickname=nickname_new
	WHERE contact_id in ( select contact_id from access a where a.username = username_new and a.trashed_date is null);

	UPDATE contact_emailaddress set email = email_new, modified = mod, modifiedby = 964 
	WHERE contact_id in ( select contact_id from access a where a.username = username_new and a.trashed_date is null);

	msg = 'OK';
	RETURN msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_update_utente(character varying, character varying, integer, integer, boolean, timestamp with time zone, character varying, character varying, character varying, character varying, text, text, character varying, character varying)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION dbi_disable_utente(usr character varying)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int;
   
BEGIN
	us_id :=  (select user_id from access a where a.username = usr and a.trashed_date is null);
	IF (us_id is not null) THEN
		UPDATE access_collegamento set enabled = false where id_utente=us_id;
	END IF;
	msg = 'OK';
	RETURN msg;
END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_disable_utente(character varying)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION dbi_enable_utente(usr character varying, struttura_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int;
   
BEGIN
	us_id :=  (select user_id from access a where a.username = usr and a.trashed_date is null);
	IF (us_id is not null) THEN
		UPDATE access_collegamento set enabled = true where id_utente=us_id and id_collegato=struttura_id;
	END IF;
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
	tot := (select count(*) from access a where a.username = usr and a.trashed_date is null);

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



CREATE OR REPLACE FUNCTION dbi_check_esistenza_utente_by_struttura (usr character varying, struttura_id integer)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;
us_id int; 
   
BEGIN
	us_id:=  (select user_id from access a where a.username = usr and a.trashed_date is null);
	tot := (select count(*) from access_collegamento u where u.id_utente=us_id and id_collegato=struttura_id);
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

CHI:Rita Mele
COSA: Aggiornamento funzione di verifica esistenza utente
QUANDO: 16/03/2015
  
  CREATE OR REPLACE FUNCTION dbi_check_esistenza_utente(usr character varying)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
tot int;   
BEGIN
	tot := (select count(*) from access a where a.username = usr and a.trashed_date is null and a.enabled);

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

