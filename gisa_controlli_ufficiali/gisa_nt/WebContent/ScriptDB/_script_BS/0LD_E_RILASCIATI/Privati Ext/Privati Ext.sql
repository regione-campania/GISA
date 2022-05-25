insert into permission_ext (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (29,'gestioneanagrafica',true,true,true,false,'GESTIONE ANAGRAFICA REVOLUTION',10,true,true);

insert into permission_ext (category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (29,'gestioneanagrafica-controlli',true,true,true,false,'GESTIONE ANAGRAFICA REVOLUTION (CONTROLLI)',10,true,true);

insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((113), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((115), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((223), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((121), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((122), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((118), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((1020), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((1123), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((224), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((225), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((227), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((228), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((118), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica'), true, true);


insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((113), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((115), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((223), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((121), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((122), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((118), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((1020), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((1123), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((224), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((225), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((227), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((228), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);
insert into role_permission_ext (role_id, permission_id, role_view, role_add) values ((118), (select permission_id from permission_ext where permission ilike 'gestioneanagrafica-controlli'), true, true);


select * from role_ext where description in ('NAC', 'GUARDIA DI FINANZA', 'CORPO FORESTALE DELLO STATO', 'NAS', 'C.F.S. AV', 'C.F.S. NA', 'C.F.S. BN', 'C.F.S. SA', 'C.F.S. CE') 