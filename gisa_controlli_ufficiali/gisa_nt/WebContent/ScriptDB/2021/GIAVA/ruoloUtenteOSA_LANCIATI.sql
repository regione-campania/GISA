insert into role_ext(role_id, role, description, enteredby, entered, modifiedby, modified, enabled, super_ruolo, descrizione_super_ruolo, in_access, in_dpat, in_nucleo_ispettivo)
values((select max(role_id)+1 from role_ext),'GIAVA','Ruolo addetto alla login per sistema GIAVA', 6567, 
now(), 6567, now(), true, 2, 'GRUPPO_ALTRE_AUTORITA', false,false, false);


-- patch da pubblicare in gisa 
package org.aspcfs.utils;
DwrGIAVA.java


-- ServiziTrasversali 
package com.pegaxchange.java.web.rest
Utenti.java;


-------------------- guc
CREATE OR REPLACE FUNCTION public.dbi_check_esistenza_utente_by_numreg( _numreg text)
  RETURNS boolean AS
$BODY$
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
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_check_esistenza_utente_by_numreg(text)
  OWNER TO postgres;

