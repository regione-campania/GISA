-- CHI: Bartolo Sansone
--  COSA: Cancellazione sistemata org
--  QUANDO: 31/10/2018

-- Sposto note1 in note_sviluppo
ALTER TABLE public.organization DISABLE TRIGGER def_cod_g2s;
ALTER TABLE public.organization DISABLE TRIGGER def_modif_date;
ALTER TABLE public.organization DISABLE TRIGGER organization_delete_materialize_view;
ALTER TABLE public.organization DISABLE TRIGGER organization_insert_materialize_view;
ALTER TABLE public.organization DISABLE TRIGGER update_modif_date;

update organization set note_sviluppo = concat_ws(';', note_sviluppo, note1) where note1 is not null;
update organization set note1 = null where note1 is not null;

ALTER TABLE public.organization ENABLE TRIGGER def_cod_g2s;
ALTER TABLE public.organization ENABLE TRIGGER def_modif_date;
ALTER TABLE public.organization ENABLE TRIGGER organization_delete_materialize_view;
ALTER TABLE public.organization ENABLE TRIGGER organization_insert_materialize_view;
ALTER TABLE public.organization ENABLE TRIGGER update_modif_date;


CREATE OR REPLACE FUNCTION public.is_org_cancellabile(_orgid integer)
  RETURNS boolean AS
$BODY$
DECLARE
	esito boolean;
	totcu integer;
BEGIN

esito:= true;
totcu:= 0;

select count(ticketid) into totcu from ticket where org_id = _orgid and trashed_date is null and tipologia = 3;

if totcu > 0 THEN
esito = false;
END IF;

 RETURN esito;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public.is_org_cancellabile(integer)
  OWNER TO postgres;
  
  
  
CREATE OR REPLACE FUNCTION public.org_delete_centralizzato(_orgid integer, _note text, _userid integer)
  RETURNS boolean AS
$BODY$
DECLARE
trasheddate timestamp without time zone;	
BEGIN

trasheddate := null;

select trashed_date into trasheddate from organization where org_id = _orgid;

if trasheddate is not null then
return false;
END IF;

update organization set trashed_date = now(), modifiedby =_userid, modified=now(), note1 = concat_ws(';', note1, _note) where org_id = _orgid;

return true;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;

  
  
  update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'accounts-accounts'
) and role_id in (1,31);
 
update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'punti_di_sbarco'
) and role_id in (1,31);

update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'operatorinonaltrove-operatorinonaltrove'
) and role_id in (1,31);

update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'riproduzioneanimale-riproduzioneanimale'
) and role_id in (1,31);


update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'osm-osm'
) and role_id in (1,31);

 
update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'osmregistrati-osmregistrati'
) and role_id in (1,31);

 
update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'farmacosorveglianza-farmacosorveglianza'
) and role_id in (1,31);

 
update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'parafarmacie-parafarmacie'
) and role_id in (1,31);

 
update role_permission set role_delete = true where permission_id in (
select permission_id from permission where permission = 'laboratori-laboratori'
) and role_id in (1,31);

  
