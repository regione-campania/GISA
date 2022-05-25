insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (29, 'suap_respingi_pratica', true, true, true, true, 'Respingi pratica', 2000, true);

insert into role_permission(permission_id, role_id, role_view, role_add, role_edit) values ((select permission_id from permission where permission ='suap_respingi_pratica'), 1, true, true, true);
insert into role_permission(permission_id, role_id, role_view, role_add, role_edit) values ((select permission_id from permission where permission ='suap_respingi_pratica'), 32, true, true, true);




CREATE OR REPLACE FUNCTION suap_respingi_pratica(_idstab integer, _idutente integer)
  RETURNS boolean AS
$BODY$
DECLARE
idOperatore integer;
altIdStabilimento integer;
noteRespingimento text;
dataattuale timestamp without time zone;
refresh boolean;

BEGIN

dataattuale := (select now());
noteRespingimento := 'Pratica respinta da HD utente '||_idutente ||' data '||dataattuale;
idOperatore := (select id_operatore from suap_ric_scia_stabilimento where id = _idstab);
altIdStabilimento := (select alt_id from suap_ric_scia_stabilimento where id = _idstab);

update suap_ric_scia_stabilimento set modified = dataattuale, modified_by = _idutente, stato_validazione = 2, validazione_da = _idutente, validazione_note = noteRespingimento where id = _idstab;
update suap_ric_scia_operatore set modified = dataattuale, modifiedby = _idutente, validato = true,  note_internal_use_only_hd = noteRespingimento where id = idOperatore;

refresh := (select * from ric_insert_into_ricerche_anagrafiche_old_materializzata(altIdStabilimento));
return true;

 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;