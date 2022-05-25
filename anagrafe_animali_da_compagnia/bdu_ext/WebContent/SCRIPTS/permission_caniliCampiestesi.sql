insert into permission (permission_id, category_id,permission,permission_view,permission_add,permission_edit,permission_delete,description , level,enabled,active)
values (586,29,'canili-campiestesi',true,true,true,false,'Modifica mq e campi estesi per i canili',10,true,true)

insert into role_permission (id, role_id, permission_id, role_edit) values (1406, 5, 586, true)
insert into role_permission (id, role_id, permission_id, role_edit) values (1406, 6, 586, true)