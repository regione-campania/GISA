select distinct 'INSERT INTO role_permission(
            id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
            role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
            entered, modified) values(' || role_permission.id || ',' || role_permission.role_id || ',' || role_permission.permission_id ||
		',' || role_permission.role_view || ',' || role_permission.role_add || ',' || role_permission.role_edit || ',' 
		|| role_permission.role_delete || ',' || role_permission.role_offline_view || ',' || role_permission.role_offline_add ||
		',' || role_permission.role_offline_edit || ',' || role_permission.role_offline_delete ||  ',''' || role_permission.entered ||
		''',''' || role_permission.modified ||
            ''');'
     
from
(
select * from
dblink('host=172.16.3.250 dbname=bdu_demo user=postgres password=postgres', '
      SELECT id, role_id, permission_id, role_view, role_add, role_edit, role_delete, 
       role_offline_view, role_offline_add, role_offline_edit, role_offline_delete, 
       entered, modified from role_permission where permission_id = 569
  ') role_permission (

  id integer,
  role_id integer ,
  permission_id integer ,
  role_view boolean ,
  role_add boolean ,
  role_edit boolean,
  role_delete boolean,
  role_offline_view boolean ,
  role_offline_add boolean ,
  role_offline_edit boolean ,
  role_offline_delete boolean ,
  entered timestamp(3) without time zone ,
  modified timestamp(3) without time zone
        ) 

         ) role_permission


         -- permission_id 561 562 563 564 565 566 568 569