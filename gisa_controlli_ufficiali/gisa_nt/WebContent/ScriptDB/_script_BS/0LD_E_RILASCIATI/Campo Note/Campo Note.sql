-- CHI: Bartolo Sansone	
-- COSA: Gestione Note Interne
-- QUANDO: 20/09/2018

alter table anagrafica.stabilimenti add column note_hd text;
alter table anagrafica.imprese add column note_hd text;
alter table anagrafica.soggetti_fisici add column note_hd text;
alter table anagrafica.rel_stabilimenti_linee_attivita add column note_hd text;


CREATE OR REPLACE FUNCTION public.get_note_hd(
    IN _riferimentoid integer,
    IN _riferimentoidnometab text)
  RETURNS TABLE(ordine_tabella integer, nome_tabella text, id_tabella integer, note_tabella text) AS
$BODY$
DECLARE
r RECORD;	

BEGIN

--organization
IF _riferimentoidnometab ='organization' THEN
FOR  ordine_tabella , nome_tabella , id_tabella , note_tabella 
in
select 1, 'organization', org_id, note_hd	from organization where org_id = _riferimentoid
order by 1 asc
LOOP RETURN NEXT;
END LOOP;
RETURN;
END IF;

--opu
IF _riferimentoidnometab ='opu_stabilimento' THEN
FOR  ordine_tabella , nome_tabella , id_tabella , note_tabella 
in
select 1, 'opu_stabilimento', id, notes_hd	from opu_stabilimento where id = _riferimentoid
UNION
select 2, 'opu_operatore', id, note_internal_use_only_hd from opu_operatore where id in (select id_operatore from opu_stabilimento where id = _riferimentoid)
UNION
select 3, 'opu_indirizzo', id, note_hd from opu_indirizzo where id in (select id_indirizzo from opu_stabilimento where id = _riferimentoid)
UNION
select 4, 'opu_soggetto_fisico', id, note_hd from opu_soggetto_fisico where id in (select id_soggetto_fisico from opu_rel_operatore_soggetto_fisico where id_operatore in (select id_operatore from opu_stabilimento where id = _riferimentoid) and enabled and data_fine is null)
UNION
select 5, 'opu_relazione_stabilimento_linee_produttive', id, concat_ws(';', note_hd,note_internal_use_hd_only) from opu_relazione_stabilimento_linee_produttive where id_stabilimento = _riferimentoid and enabled
order by 1 asc
LOOP RETURN NEXT;
END LOOP;
RETURN;
END IF;

--sintesis
IF _riferimentoidnometab ='sintesis_stabilimento' THEN
FOR  ordine_tabella , nome_tabella , id_tabella , note_tabella 
in
select 1, 'sintesis_stabilimento', id, notes_hd	from sintesis_stabilimento where id = _riferimentoid
UNION
select 2, 'sintesis_operatore', id, note_internal_use_only_hd from sintesis_operatore where id in (select id_operatore from sintesis_stabilimento where id = _riferimentoid)
UNION
select 3, 'sintesis_indirizzo', id, note_hd from sintesis_indirizzo where id in (select id_indirizzo from sintesis_stabilimento where id = _riferimentoid)
UNION
select 4, 'sintesis_soggetto_fisico', id, note_hd from sintesis_soggetto_fisico where id in (select id_soggetto_fisico from sintesis_rel_operatore_soggetto_fisico where id_operatore in (select id_operatore from sintesis_stabilimento where id = _riferimentoid) and enabled and data_fine is null)
UNION
select 5, 'sintesis_relazione_stabilimento_linee_produttive', id, note_internal_use_hd_only from sintesis_relazione_stabilimento_linee_produttive where id_stabilimento = _riferimentoid and enabled
order by 1 asc
LOOP RETURN NEXT;
END LOOP;
RETURN;
END IF;

--api
IF _riferimentoidnometab ='apicoltura_apiari' THEN
FOR  ordine_tabella , nome_tabella , id_tabella , note_tabella 
in
select 1, 'apicoltura_apiari', id,  concat_ws(';', note_hd,note_internal_use_hd_only) 	from apicoltura_apiari where id = _riferimentoid
UNION
select 2, 'apicoltura_imprese', id, note_hd from apicoltura_imprese where id in (select id_operatore from apicoltura_apiari where id = _riferimentoid)
UNION
select 3, 'opu_indirizzo', id, note_hd from opu_indirizzo where id in (select id_indirizzo from apicoltura_apiari where id = _riferimentoid)
UNION
select 4, 'opu_soggetto_fisico', id, note_hd from opu_soggetto_fisico where id in (select id_soggetto_fisico from apicoltura_apiari where id = _riferimentoid)
order by 1 asc
LOOP RETURN NEXT;
END LOOP;
RETURN;
END IF;

--gestione anagrafica
IF _riferimentoidnometab ='anagrafica.stabilimenti' THEN
FOR  ordine_tabella , nome_tabella , id_tabella , note_tabella 
in
select 1, 'anagrafica.stabilimenti', id, note_hd from anagrafica.stabilimenti where id = _riferimentoid
UNION
select 2, 'anagrafica.imprese', id, note_hd from anagrafica.imprese where id in (select id_impresa from anagrafica.rel_imprese_stabilimenti where id_stabilimento = _riferimentoid and data_scadenza is null and data_cancellazione is null)
UNION
select 3, 'anagrafica.indirizzi', id, note_hd from anagrafica.indirizzi where id in (select id_indirizzo from anagrafica.rel_stabilimenti_indirizzi where id_stabilimento = _riferimentoid and data_scadenza is null and data_cancellazione is null)
UNION
select 4, 'anagrafica.soggetti_fisici', id, note_hd from anagrafica.soggetti_fisici where id in (select id_soggetto_fisico from anagrafica.rel_imprese_soggetti_fisici where id_impresa in (select id_impresa from anagrafica.rel_imprese_stabilimenti where id_stabilimento = _riferimentoid and data_scadenza is null and data_cancellazione is null) and data_scadenza is null and data_cancellazione is null)
UNION
select 5, 'anagrafica.rel_stabilimenti_linee_attivita', id, note_hd from anagrafica.rel_stabilimenti_linee_attivita where id_stabilimento = _riferimentoid and data_scadenza is null and data_cancellazione is null
order by 1 asc
LOOP RETURN NEXT;
END LOOP;
RETURN;
END IF;
     
 END;


$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_note_hd(integer, text)
  OWNER TO postgres;


  --Permessi
insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (94, 'note_hd', true, false, false, false, 'Visualizza note HD sulle anagrafiche', 2000, true);

insert into role_permission(permission_id, role_id, role_view, role_add, role_edit) values ((select permission_id from permission where permission ='note_hd'), 1, true, true, true);
insert into role_permission(permission_id, role_id, role_view, role_add, role_edit) values ((select permission_id from permission where permission ='note_hd'), 32, true, true, true);


