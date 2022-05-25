insert into role_ext (role_id,role, description, enteredby, modifiedby, modified, enabled, role_type, carico_default, super_ruolo, descrizione_super_ruolo, in_access, in_dpat, in_nucleo_ispettivo, enabled_as_qualifica, view_lista_utenti_nucleo_ispettivo) values 
                     ((select max(role_id)+1 from role_ext), 'ESERCITO ITALIANO', 'ESERCITO ITALIANO', 6567,6567,current_timestamp,true,0,0,2,'GRUPPO_ALTRE_AUTORITA',true,false, true, false, false);
                     
-- copio da nas ce
select 'insert into role_permission_ext(role_id, permission_id, role_view, role_add, role_edit, role_delete, entered) values (10000009,'||permission_id||','||role_view||','||role_add||','||role_edit||','||role_delete||',current_timestamp);'
from role_permission_ext  where role_id  = 118;

select 'update role_permission_ext set role_view=false where permission_id = '||permission_id||' and role_id = 10000009;' 
from permission_ext  where permission ilike '%stabilimenti-stabilimenti%' 

select * from permission_ext where permission  ilike '%stabilimenti' -- 238
select * from role_permission_ext where permission_id = 238 and role_id =10000009

update role_permission_ext set role_view=false where role_id = 10000009 and permission_id=238;


update role_permission_ext set role_add=false, role_edit=false where role_id = 10000009 and permission_id=1306; --acque di rete

--update contact_ext set namefirst = 'MARIO FRANCESCO', namelast = 'ESPOSITO' where contact_id = (select contact_id from access_ext where username='spid_usr_13626')