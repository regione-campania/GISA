insert into permission (category_id, permission, permission_view, permission_edit, permission_add, permission_delete, description) values ((select category_id from permission_category where category ilike 'sintesis'), 'sintesis-prodotti', true, true, true, true, 'Gestione Anagrafiche SINTESIS: Gestione prodotti');


select 'insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values (' || role_id ||', (select permission_id from permission where permission = ''sintesis-prodotti''), true, true, true, true);' from role_permission where permission_id in (select permission_id from permission where permission ilike 'sintesis') and role_edit


delete from role_permission where role_id not in (1, 31, 40, 32, 27, 39, 30, 324, 28, 53) and permission_id in (select permission_id from permission where permission = 'sintesis')
delete from role_permission_ext  where permission_id in (select permission_id from permission_ext where permission = 'sintesis')
