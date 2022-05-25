select * from permission where permission ilike '%gestione_schede_supplementari%' -- verifico id permesso per 793
-- delete from permission where permission_id = 794

update permission set permission = 'gestione-schede-supplementari' where permission_id = 793;
insert into role(role, description, enteredby, entered, modifiedby, modified, enabled, super_ruolo, descrizione_super_ruolo, in_access, in_dpat, in_nucleo_ispettivo)
values('Gestore schede supplementari','Ruolo addetto alla gestione delle schede supplementari in un determinato stabilimento o in una lista di stabilimenti', 6567, 
now(), 6567, now(), true, 1, 'RUOLO GISA', true,false, false);

select * from role where role  ilike '%Gestore %' --329 role_id 
insert into role_permission (role_id, permission_id, role_view, role_add) values ((select role_id from role where  description ilike '%supplementari%'),793,true, true);

--793
select 'insert into role_permission (role_id, permission_id, role_view, role_add) values (329,'||permission_id||','||role_view||','||role_add||');',
* from role_permission 
where role_id = 3372 -- ruolo gestore macelli

 delete from role_permission where permission_id=(select permission_id from permission where permission ilike '%macelli%') and role_id = 329 -- cancella il permesso del gestore macelli 
 insert into role_permission(role_id, permission_id, role_view, role_add) values (329,793, true,true);
 

CREATE TABLE public.utenti_anagrafiche_schede_supplementari
(
  id serial,
  username text,
  riferimento_id integer,
  riferimento_id_nome_tab text,
  entered timestamp without time zone DEFAULT now(),
  trashed_date timestamp without time zone,
  note_hd text
 )
WITH (
  OIDS=FALSE
);
ALTER TABLE public.utenti_anagrafiche_schede_supplementari
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.get_anagrafiche_schede_associate_utente(IN _username text)
  RETURNS SETOF utenti_anagrafiche_schede_supplementari AS
$BODY$
DECLARE
	
BEGIN
		return query
			select * FROM utenti_anagrafiche_schede_supplementari WHERE username = _username and trashed_date is null;
END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_anagrafiche_schede_associate_utente(text)
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public.associa_utente_schede_supplementari(
    _username text,
    _riferimento_id integer,
    _riferimento_id_nome_tab text)
  RETURNS text AS
$BODY$
DECLARE
msg text;
idRuoloScheda integer;
idUtenteScheda integer;
numReg text;

BEGIN

numReg := (select distinct coalesce(n_reg, n_linea) from ricerche_anagrafiche_old_materializzata where riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab);

select role_id into idRuoloScheda from access where username = _username and role_id in (select role_id from role where role ilike 'Gestore Schede Supplementari');

IF idRuoloScheda is null or idRuoloScheda <=0 THEN
msg := 'KO - Questo utente non risulta esistente e/o associato al ruolo GESTORE SCHEDE SUPPLEMENTARI.';
RETURN msg;
END IF;

select id into idUtenteScheda from utenti_anagrafiche_schede_supplementari where username = _username and riferimento_id = _riferimento_id and riferimento_id_nome_tab = _riferimento_id_nome_tab and trashed_date is null;

IF idUtenteScheda >0 THEN
msg := 'KO - Questo utente risulta attualmente associato allo stabilimento con numero di registrazione ' ||numReg|| '';
RETURN msg;
END IF;

insert into utenti_anagrafiche_schede_supplementari (username, riferimento_id, riferimento_id_nome_tab, note_hd) values (_username, _riferimento_id, _riferimento_id_nome_tab, 'Inserito tramite DBI');

msg := 'OK - Inserita associazione tra utente ' || _username || ' e stabilimento con numero di registrazione '||numReg|| '' ;
RETURN msg;

END;
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.associa_utente_schede_supplementari(text, integer, text)
  OWNER TO postgres;


-- per i test soltanto 
--insert into utenti_anagrafiche_schede_supplementari(username, riferimento_id, riferimento_id_nome_tab, entered) values('vellutog',277359 ,'opu_stabilimento', now());

--select * from associa_utente_schede_supplementari('vellutog',183564,'opu_stabilimento')
