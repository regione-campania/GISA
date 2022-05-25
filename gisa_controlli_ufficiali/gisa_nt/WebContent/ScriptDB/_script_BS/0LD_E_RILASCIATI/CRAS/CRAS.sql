-- Creo ruolo in GISA

select * from role where role_id = 333;
insert into role(role_id, role, description, enteredby, modifiedby, super_ruolo, descrizione_super_ruolo, in_nucleo_ispettivo) values (333, 'C.R.A.S.', 'C.R.A.S.', 1, 1,1,'RUOLO GISA', true) returning role_id;
-- SE NON HA INSERITO ROLE_ID 333 OCCORRE FARE LA MODIFICA IN ADMIN.BASE.ROLES

-- Associo un sottoinsieme di permessi del NAS da EXT

insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete)  
select (select role_id from role where role = 'C.R.A.S.'), pg.permission_id, rp.role_view, rp.role_add, rp.role_edit, rp.role_delete from role_permission_ext rp 
left join permission_ext p on p.permission_id = rp.permission_id
left join permission pg on pg.permission = p.permission
where rp.role_id = 118
and p.permission in (
'campioni-campioni',
'accounts-accounts-campioni',
'stabilimenti-stabilimenti-campioni',
'soa-soa-campioni',
'osmregistrati-osmregistrati-campioni',
'molluschibivalvi-campioni',
'osm-osm-campioni',
'abusivismi-abusivismi-campioni',
'distributori-distributori-campioni',
'operatoriregione-operatoriregione-campioni',
'imbarcazioni-imbarcazioni-campioni',
'canili-campioni',
'operatori-commerciali-campioni',
'punti_di_sbarco-campioni',
'acquedirete-acquedirete-campioni',
'operatoriprivati-operatoriprivati-campioni',
'farmacie-farmacie-campioni',
'osa-osa-campioni',
'riproduzioneanimale-campioni',
'myhomepage',
'accounts-accounts-vigilanza',
'canipadronali-campioni',
'globalitems-myitems',
'globalitems-recentitems',
'zonecontrollo-vigilanza',
'riproduzioneanimale-vigilanza',
'zonecontrollo-campioni',
'oia-oia-vigilanza',
'colonie-vigilanza',
'colonie-campioni',
'oia-oia-campioni',
'operatorinonaltrove-operatorinonaltrove-vigilanza',
'operatoriprivati-operatoriprivati-vigilanza',
'operatorinonaltrove-operatorinonaltrove-campioni',
'laboratorihaccp-laboratorihaccp-vigilanza',
'laboratorihaccp-laboratorihaccp-campioni',
'canipadronali-vigilanza',
'parafarmacie-parafarmacie-vigilanza',
'opu-campioni',
'farmacie-farmacie-vigilanza',
'vigilanza',
'vigilanza-vigilanza',
'osa-osa-vigilanza',
'myhomepage-profile',
'myhomepage-action-lists',
'myhomepage-action-plans',
'stabilimenti-stabilimenti-vigilanza',
'soa-soa-vigilanza',
'trasporti-trasporti-vigilanza',
'osmregistrati-osmregistrati-vigilanza',
'opu-vigilanza',
'aziendeagricole-vigilanza',
'molluschibivalvi-vigilanza',
'osm-osm-vigilanza',
'canili-vigilanza',
'allevamenti-allevamenti-vigilanza',
'abusivismi-abusivismi-vigilanza',
'guidautente',
'distributori-distributori-vigilanza',
'operatori-commerciali-vigilanza',
'operatoriregione-operatoriregione-vigilanza',
'imbarcazioni-imbarcazioni-vigilanza',
'allevamenti-allevamenti-campioni',
'punti_di_sbarco-vigilanza',
'myhomepage-dashboard',
'myhomepage-inbox',
'myhomepage-messaggi',
'acquedirete-vigilanza',
'sintesis-sintesis-vigilanza',
'sintesis-sintesis-campioni',
'opu');

--Aggiusto ispezione
update lookup_tipo_controllo set level = 11 where description = 'Attivita giornaliera di ispezione carni al macello';

insert into permission (category_id, permission, permission_view, description) values (2, 'cu_solo_ispezionesemplice', true, 'Visualizza solo ISPEZIONE SEMPLICE nei controlli ufficiali');
insert into permission (category_id, permission, permission_view, description) values (2, 'cu_solo_pianob7', true, 'Visualizza solo B7 nella lista piani dei controlli ufficiali');


insert into role_permission (role_id, permission_id, role_view) values ((select role_id from role where role='C.R.A.S.'), (select permission_id from permission where permission ='cu_solo_ispezionesemplice'), true);

insert into role_permission (role_id, permission_id, role_view) values ((select role_id from role where role='C.R.A.S.'), (select permission_id from permission where permission ='cu_solo_pianob7'), true);


-- CREO DUE PERMESSI NUOVI

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints)
select category_id, 'pratiche_suap', permission_view, permission_add, permission_edit, permission_delete, 'Cavaliere Pratiche SUAP', level, enabled, active, viewpoints from permission where permission = 'opu';

insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints)
select category_id, 'altri_stabilimenti', permission_view, permission_add, permission_edit, permission_delete, 'Cavaliere Pratiche SUAP', level, enabled, active, viewpoints from permission where permission = 'opu';

-- ASSEGNO I NUOVI PERMESSI A TUTTI QUELLI CHE HANNO OPU TRANNE CRAS

insert into role_permission (role_id, permission_id, role_view, role_add, role_edit, role_delete)
select role_id, (select permission_id from permission where permission='pratiche_suap'), role_view, role_add, role_edit, role_delete from role_permission where permission_id in (select permission_id from permission where permission='opu') and role_id not in (select role_id from role where role = 'C.R.A.S.');


insert into role_permission (role_id, permission_id, role_view, role_add, role_edit, role_delete)
select role_id, (select permission_id from permission where permission='altri_stabilimenti'), role_view, role_add, role_edit, role_delete from role_permission where permission_id in (select permission_id from permission where permission='opu') and role_id not in (select role_id from role where role = 'C.R.A.S.');

-- CREO DUE PERMESSI NUOVI

insert into permission_ext (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints)
select category_id, 'pratiche_suap', permission_view, permission_add, permission_edit, permission_delete, 'Cavaliere Pratiche SUAP', level, enabled, active, viewpoints from permission_ext where permission = 'opu';

insert into permission_ext (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled, active, viewpoints)
select category_id, 'altri_stabilimenti', permission_view, permission_add, permission_edit, permission_delete, 'Cavaliere Pratiche SUAP', level, enabled, active, viewpoints from permission_ext where permission = 'opu';

--- FIXAGGIO B7

update lookup_piano_monitoraggio set codice_interno = 1634 where alias ilike '%b7%' and anno = 2018 and codice_interno is null;

update controlli_ufficiali_motivi_ispezione set codice_int_piano = 1634 where descrizione_piano ilike '%b7%' and
(data_scadenza > (now() + '1 day'::interval) OR data_scadenza IS NULL) 



--- Cambio idea opu -> zone di controllo


insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete)  
select (select role_id from role where role = 'C.R.A.S.'), (select permission_id from permission where permission= 'macroarea'), true, true, false, false;

insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete)  
select (select role_id from role where role = 'C.R.A.S.'), (select permission_id from permission where permission= 'zonecontrollo' limit 1), true, true, false, false ;

delete from role_permission where role_id in (select role_id from role where role = 'C.R.A.S.') and permission_id in (select permission_id from permission where permission= 'opu')


