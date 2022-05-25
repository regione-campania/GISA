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
  OWNER TO admin;


CREATE OR REPLACE FUNCTION dbi_insert_utente( character varying,  character varying,  integer,  integer,  integer,  boolean,  integer,  character varying,  character varying,  character varying,  text,  text,  character varying,  character varying,  timestamp with time zone)
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

		con_id=currVal('contact_contact_id_seq');
		us_id=currVal('access_user_id_seq');
		INSERT INTO contact ( contact_id, user_id, namefirst, namelast, enteredby, modifiedby, site_id, codice_fiscale, notes, enabled,luogo,nickname ) 
		VALUES ( con_id, us_id, namefirst1, namelast1, 964, 964, site_id1, cf1, notes1, enabled1,luogo1,nickname1 );
			
		con_id=currVal('contact_contact_id_seq');
		INSERT INTO contact_emailaddress(contact_id, emailaddress_type, email, enteredby, modifiedby, primary_email)
		VALUES (con_id, 1, email1, 964, 964, true);
	END IF;
	
	msg = 'OK';
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone)
  OWNER TO admin;


CREATE OR REPLACE FUNCTION dbi_update_utente( character varying, character varying,  integer,  integer,  boolean,  timestamp with time zone,  character varying,  character varying,  character varying,  character varying,  text,  text,  character varying,  character varying)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
mod timestamp without time zone;


password_new character varying := $1;
username_new character varying := $2;
site_id_new integer := $3;
role_id_new integer := $4;
enabled_new boolean := $5;
expires_new timestamp with time zone := $6;
old_username character varying := $7;
namefirst_new character varying := $8;
namelast_new character varying := $9;
cf character varying := $10;
notes_new text := $11;
luogo_new text := $12;
nickname_new character varying := $13;
email_new character varying   := $14;
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
  OWNER TO admin;


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
  OWNER TO admin;