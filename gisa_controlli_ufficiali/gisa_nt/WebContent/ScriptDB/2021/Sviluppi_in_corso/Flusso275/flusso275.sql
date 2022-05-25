-- creazione permesso di inserimento/modifica codice SINVSA
insert into permission(permission, category_id, description, permission_view)
values('inserimento-codice-sinvsa', (select category_id from permission_category where category = 'Stabilimenti'),
	   'Abilita l''inserimento e la modifica del codice SINVSA', true);
	   
-- assegnazione permesso 'inserimento-codice-sinvsa' ad HD I, HD II e ORSA
insert into role_permission(role_id, permission_id, role_view) 
values
((select role_id from role where role = 'ORSA'), (select permission_id from permission where permission = 'inserimento-codice-sinvsa'), true),
((select role_id from role where role = 'Amministratore Sistema HD I Livello'), (select permission_id from permission where permission = 'inserimento-codice-sinvsa'), true),
((select role_id from role where role = 'Amministratore Sistema HD II livello'), (select permission_id from permission where permission = 'inserimento-codice-sinvsa'), true);

-- creazione della tabella contenente il codice SINVSA di una determinata anagrafica
create table if not exists anagrafica_codice_sinvsa(
	id serial primary key,
	riferimento_id int not null,
	riferimento_id_nome_tab varchar not null,
	codice_sinvsa varchar not null,
	data_codice_sinvsa timestamp not null,
	entered timestamp,
	entered_by int,
	trashed_date timestamp,
	note_hd text
);

alter table anagrafica_codice_sinvsa owner to postgres;
