-- inserimento nuovo permesso "lista-services"
insert into permission(permission, category_id, permission_view, description)
values('lista-services', (select category_id from permission_category where category = 'Sistema'), true, 'Lista Chiamate ai Web Services');

-- assegnazione permesso a HD I e HD II
insert into role_permission(role_id, permission_id, role_view)
values(
	(select role_id from role where role ilike '%hd i livello%'), 
	(select permission_id from permission where permission = 'lista-services'),
	true
),
values(
	(select role_id from role where role ilike '%hd ii livello%'), 
	(select permission_id from permission where permission = 'lista-services'),
	true
);