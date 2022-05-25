-- CHI: Bartolo Sansone	
-- COSA: Gestione Cambio sede legale
-- QUANDO: 30/07/2018

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (29, 'cambio_sede_operatore', true, true, true, true, 'Cambio sede legale', 2000, true);

insert into role_permission(permission_id, role_id, role_view, role_add, role_edit) values ((select permission_id from permission where permission ='cambio_sede_operatore'), 1, true, true, true);

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (29, 'cambio_sede_operatore_tutte_asl', true, true, true, true, 'Cambio Sede Legale - vedi TUTTE le ASL', 2000, true);

insert into role_permission(permission_id, role_id, role_view, role_add, role_edit) values ((select permission_id from permission where permission ='cambio_sede_operatore_tutte_asl'), 1, true, true, true);

insert into permission (category_id, permission, permission_view, description, level, enabled)
values (29, 'cambio_sede_operatore_storico', true, 'Cambio Sede Legale Storico', 2000, true);

insert into role_permission(permission_id, role_id, role_view) values ((select permission_id from permission where permission ='cambio_sede_operatore_storico'), 1, true);

create table opu_cambio_sede_legale_storico(id serial primary key, data_cambio timestamp without time zone default now(), id_utente integer, id_stabilimento integer, id_operatore integer, id_indirizzo_vecchio integer, id_indirizzo_nuovo integer, id_asl_vecchia integer, id_asl_nuova integer, log text);

alter table opu_operatore add column note_cambio text;

CREATE OR REPLACE FUNCTION public_functions.cambio_sede_operatore(
    _idstab integer,
    _idasl integer,
    _idcomune integer,
    _idtoponimo integer,
    _via text,
    _civico text,
    _cap text,
    _lat double precision,
    _lon double precision,
    _idutente integer)
  RETURNS text AS
$BODY$
DECLARE
codice integer;
msg text;
idOperatore integer;
dataattuale timestamp without time zone;
idIndirizzoNuovo integer;
idProvincia text;
idAslVecchia integer;
idIndirizzoVecchio integer;
idIndirizzoVecchioStab integer;
refresh boolean;
noteCambio text;
log text;

BEGIN

codice:= -1;
msg:= '';
dataattuale := (select now());
idIndirizzoNuovo:= -1;
idIndirizzoVecchio := -1;
idIndirizzoVecchioStab := -1;
idProvincia:='';
idOperatore = -1;

-- SALVO DATI ATTUALI

select id_asl, id_indirizzo, id_operatore into idAslVecchia, idIndirizzoVecchioStab, idOperatore from opu_stabilimento where id = _idstab;

raise info 'ID INDIRIZZO VECCHIO STAB: %', idIndirizzoVecchioStab;
raise info 'ID ASL VECCHIA: %', idAslVecchia;
raise info 'ID OPERATORE: %', idOperatore;

log:=concat_ws(';', log, 'ID INDIRIZZO VECCHIO STAB', idIndirizzoVecchioStab);
log:=concat_ws(';', log, 'ID ASL VECCHIA', idAslVecchia);
log:=concat_ws(';', log, 'ID OPERATORE', idOperatore);

select id_indirizzo into idIndirizzoVecchio from opu_operatore where id = idOperatore;

raise info 'ID INDIRIZZO VECCHIO: %', idIndirizzoVecchio;
log:=concat_ws(';', log, 'ID INDIRIZZO VECCHIO', idIndirizzoVecchio);

-- INSERIMENTO NUOVO INDIRIZZO

idProvincia:= (select cod_provincia::integer||'' from comuni1 where id = _idcomune);
idIndirizzoNuovo=(select id from opu_indirizzo where comune = _idcomune and via ilike _via and cap = _cap and toponimo = _idtoponimo and civico ilike _civico limit 1);

raise info 'ID INDIRIZZO TROVATO: %', idIndirizzoNuovo;
log:=concat_ws(';', log, 'ID INDIRIZZO TROVATO', idIndirizzoNuovo);

if idIndirizzoNuovo is null THEN
insert into opu_indirizzo(nazione, cap, provincia, comune, toponimo, via, civico, latitudine, longitudine) values ('Italia', _cap, idProvincia, _idcomune, _idtoponimo, _via, _civico, _lat, _lon) returning id into idIndirizzoNuovo;

raise info 'ID INDIRIZZO INSERITO: %', idIndirizzoNuovo;
log:=concat_ws(';', log, 'ID INDIRIZZO INSERITO', idIndirizzoNuovo);

END IF;

-- AGGIORNAMENTO INDIRIZZO OPERATORE

noteCambio := concat('Sede Legale modificata da ', (select concat_ws(' ', t.description, i.via, i.civico, c.nome, a.description)
from  opu_indirizzo i 
left join lookup_toponimi t on t.code = i.toponimo
left join comuni1 c on c.id = i.comune
left join lookup_site_id a on a.code = c.codiceistatasl::integer
where i.id =idIndirizzoVecchio), ' a ', (select concat_ws(' ', t.description, i.via, i.civico, c.nome, a.description)
from  opu_indirizzo i 
left join lookup_toponimi t on t.code = i.toponimo
left join comuni1 c on c.id = i.comune
left join lookup_site_id a on a.code = c.codiceistatasl::integer
where i.id =idIndirizzoNuovo), ' da utente ',_idutente , ' in data ', dataattuale );

update opu_operatore set id_indirizzo = idIndirizzoNuovo, note_cambio = concat_ws(';', note_cambio, noteCambio) where id = idOperatore;

raise info 'AGGIORNATO INDIRIZZO OPERATORE DA % A %', idIndirizzoVecchio, idIndirizzoNuovo;
log:=concat_ws(';', log, 'AGGIORNATO INDIRIZZO OPERATORE DA',idIndirizzoVecchio, 'A', idIndirizzoNuovo);

update opu_stabilimento set id_asl = _idasl, id_indirizzo = idIndirizzoNuovo, notes_hd = concat_ws(';', notes_hd, noteCambio) where id = _idstab;

raise info 'AGGIORNATO INDIRIZZO STABILIMENTO DA % A %', idIndirizzoVecchioStab, idIndirizzoNuovo;
log:=concat_ws(';', log, 'AGGIORNATO INDIRIZZO STABILIMENTO DA',idIndirizzoVecchioStab, 'A', idIndirizzoNuovo);

-- SCRIVO NELLO STORICO

insert into opu_cambio_sede_legale_storico(data_cambio, id_utente, id_stabilimento, id_operatore, id_indirizzo_vecchio, id_indirizzo_nuovo, id_asl_vecchia, id_asl_nuova, log) values (dataattuale, _idutente, _idstab, idOperatore,  idIndirizzoVecchio, idIndirizzoNuovo, idAslVecchia, _idasl, log);

-- AGGIORNO RICERCA PER STABILIMENTI
refresh = (select * from opu_insert_into_ricerche_anagrafiche_old_materializzata(_idstab));
raise info 'AGGIORNATA TABELLA RICERCHE STABILIMENTO VECCHIO: %', _idstab;

codice=1;
msg='SEDE LEGALE CAMBIATA CON SUCCESSO';

return codice||';;'||msg||';;'||_idstab;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.cambio_sede_operatore(integer, integer, integer, integer, text, text, text, double precision, double precision, integer)
  OWNER TO postgres;

  
  
  

  
  
  
