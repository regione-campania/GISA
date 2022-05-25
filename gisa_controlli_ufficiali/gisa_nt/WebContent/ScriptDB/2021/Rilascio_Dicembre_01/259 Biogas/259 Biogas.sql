-- Chi: Bartolo Sansone
-- Cosa: Flusso 259 Gestione Biogas
-- Quando: 27/09/21 

CREATE TABLE adeguamento_scheda_biogas (id serial primary key, org_id integer, alt_id integer, impianto_biogas text, tipologia_biogas text,  enteredby integer, entered timestamp without time zone default now(), trashed_date timestamp without time zone);

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (102, 'adeguamento-schedabiogas', true, true, true, true, 'ADEGUAMENTI: Inserimento Scheda Biogas', 400, true);

insert into role_permission(permission_id, role_id, role_view, role_add) values ((select permission_id from permission where permission ='adeguamento-schedabiogas'), 1, true, true);

insert into role_permission(permission_id, role_id, role_view, role_add) values ((select permission_id from permission where permission ='adeguamento-schedabiogas'), 31, true, true);

insert into role_permission(permission_id, role_id, role_view, role_add) values ((select permission_id from permission where permission ='adeguamento-schedabiogas'), 32, true, true);

insert into role_permission(permission_id, role_id, role_view, role_add)
select (select permission_id from permission where permission ='adeguamento-schedabiogas'), role_id, true, true
from role where role_id in (16,19, 21, 41,42, 43, 44, 45, 46, 54, 56, 59, 96, 97, 98, 120, 221, 222)

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (102, 'adeguamento-schedabiogas-ignora', true, false, false, false, 'ADEGUAMENTI: Inserimento Controlli Ufficiali ignorando presenza Scheda Biogas', 400, true);

insert into role_permission(permission_id, role_id, role_view) values ((select permission_id from permission where permission ='adeguamento-schedabiogas-ignora'), 1, true);

insert into role_permission(permission_id, role_id, role_view) values ((select permission_id from permission where permission ='adeguamento-schedabiogas-ignora'), 31, true);

insert into role_permission(permission_id, role_id, role_view) values ((select permission_id from permission where permission ='adeguamento-schedabiogas-ignora'), 32, true);

insert into role_permission(permission_id, role_id, role_view, role_add)
select (select permission_id from permission where permission ='adeguamento-schedabiogas'), role_id, true, true
from role where role_id in (select role_id from role where role ilike '%polo%')


