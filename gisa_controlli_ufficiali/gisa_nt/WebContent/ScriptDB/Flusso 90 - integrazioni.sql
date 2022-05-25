--REGIONE, ORSA e HD
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete) values (40,708,true,false,false,false);
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete) values (31,708,true,false,false,false);
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete) values (32,708,true,false,false,false);
insert into role_permission (role_id,permission_id,role_view,role_add,role_edit,role_delete) values (1,708,true,false,false,false);

--HELP DESK SU GISA_EXT
insert into role_permission_ext (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values (270,10000028,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission_ext (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values (270,10000026,true,false,false,false,false,false,false,false,now(),now());
insert into role_permission_ext (role_id,permission_id,role_view,role_add,role_edit,role_delete,role_offline_view,role_offline_add,role_offline_edit,role_offline_delete,entered,modified) values (270,99,      true,false,false,false,false,false,false,false,now(),now());

--PERMESSI DI AGGIUNTA HD SU GISA
update role_permission set role_add = true, role_edit = true where role_id in (1,32) and permission_id = 708;