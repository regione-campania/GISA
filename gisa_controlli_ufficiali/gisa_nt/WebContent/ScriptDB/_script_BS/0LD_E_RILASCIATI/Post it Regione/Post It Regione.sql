alter table post_it add column tipo integer default 1;
update permission set permission = 'gestionepostit_asl', description = 'Gestione Messaggio Post It (ASL)'  where permission = 'gestionepostit';
insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
select category_id, 'gestionepostit_regione', permission_view, permission_add, permission_edit, permission_delete, 'Gestione Messaggio Post It (REGIONE)', level, enabled from permission where permission = 'gestionepostit_asl';