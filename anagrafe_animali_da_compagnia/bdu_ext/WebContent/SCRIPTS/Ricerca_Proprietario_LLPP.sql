INSERT INTO permission(
            permission_id, category_id, permission, permission_view, permission_add, 
            permission_edit, permission_delete, description, level, enabled, 
            active, viewpoints, permission_offline_view, permission_offline_add, 
            permission_offline_edit, permission_offline_delete, entered, 
            modified)
    VALUES ( 585, 12, 'myhomepage_ricerca_proprietario_lp', true, false, 
            false, false, 'Ricerca Proprietario per LLPP', 160, true, 
            true, false, false, false, 
            false,false, now(), 
            now());





insert into role_permission values(    (select max(id) from role_permission) + 1,5, 585, true,false,false,false,false,false,false,false,now(),now() );
insert into role_permission values(    (select max(id) from role_permission) + 1,6 , 585, true,false,false,false,false,false,false,false,now(),now() );
insert into role_permission values(    (select max(id) from role_permission) + 1,24 , 585, true,false,false,false,false,false,false,false,now(),now() );