------------------------------------------------------------ Operazioni preliminari allineamento permessi e ruoli

-- Svuoto permessi per responsabile regione
delete from role_permission where role_id = 27;

-- Genero permessi per responsabile regione copiandoli da ORSA
insert into role_permission (role_id, permission_id, role_view, role_add, role_edit, role_delete, note_hd)
select 27, permission_id, role_view, role_add, role_edit, role_delete, note_hd from role_permission where role_id = 31;

-- Inserisco nuovo ruolo Referente Regione copiandolo da Responsabile Regione

select * from dbi_insert_ruolo('Referente Regione', 'Referente Regione', 27, true, false, false, false, true);

-- Aggiorno sarnelli a responsabile regione

-- gisa
update access_ set role_id = 27, note_hd = 'Ruolo modificato a Responsabile Regione mail b.sansone 22/12/21 Aggiornamento Ruoli Regionali' where username = 'plsrn' and data_scadenza is null;

-- guc
update guc_ruoli set ruolo_integer = 27, ruolo_string = 'Responsabile Regione', note = 'Ruolo modificato a Responsabile Regione mail b.sansone 22/12/21 Aggiornamento Ruoli Regionali' where id_utente in (select id from guc_utenti where username = 'plsrn') and endpoint = 'Gisa';

-- Aggiorno matonti a referente regione

select role_id from role where role = 'Referente Regione';
-- Ricalcolare id ruolo Referente Regione
-- Id attuale: 3374 

-- gisa
update access_ set role_id = 3374, note_hd = 'Ruolo modificato a Referente Regione mail b.sansone 22/12/21 Aggiornamento Ruoli Regionali' where username = 'c.matonti' and data_scadenza is null;

-- guc
update guc_ruoli set ruolo_integer = 3374, ruolo_string = 'Referente Regione', note = 'Ruolo modificato a Referente Regione mail b.sansone 22/12/21 Aggiornamento Ruoli Regionali' where id_utente in (select id from guc_utenti where username = 'c.matonti') and endpoint = 'Gisa';

-- Aggiorno di loria a referente regione

-- gisa
update access_ set role_id = 3374, note_hd = 'Ruolo modificato a Referente Regione mail b.sansone 22/12/21 Aggiornamento Ruoli Regionali' where username = 'gdiloriaNU' and data_scadenza is null;

-- guc
update guc_ruoli set ruolo_integer = 3374, ruolo_string = 'Referente Regione', note = 'Ruolo modificato a Referente Regione mail b.sansone 22/12/21 Aggiornamento Ruoli Regionali' where id_utente in (select id from guc_utenti where username = 'gdiloriaNU') and endpoint = 'Gisa';

------------------------------------------------------------ Operazioni Gestore Flussi

-- aggiunta entita' modulo A
insert into lookup_tipo_modulo_sviluppo(code, description)
values (5, 'A - Richieste Regionali');

-- aggiunta entita' modulo VCE
insert into lookup_tipo_modulo_sviluppo(code, description)
values (6, 'VCE - Validazione Collaudo Esterno');

-- aggiunta entita' modulo AL
insert into lookup_tipo_modulo_sviluppo(code, description) values(
	7, 
	'AL - Allegati (Generici)'
);

-- creazione tabella lookup priorita'
-- Table: public.lookup_priorita_flusso
-- DROP TABLE IF EXISTS public.lookup_priorita_flusso;

CREATE TABLE IF NOT EXISTS public.lookup_priorita_flusso
(
    code serial,
    description character varying(50) COLLATE pg_catalog."default" NOT NULL,
    default_item boolean DEFAULT false,
    level integer DEFAULT 0,
    enabled boolean DEFAULT true,
    CONSTRAINT lookup_priorita_flusso_pkey PRIMARY KEY (code)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.lookup_priorita_flusso
    OWNER to postgres;
    
-- aggiunta priorita' flusso in tabella lookup
insert into lookup_priorita_flusso(description, level) values('Non Definita', 0);
insert into lookup_priorita_flusso(description, level) values('Bassa', 1);
insert into lookup_priorita_flusso(description, level) values('Media', 2);
insert into lookup_priorita_flusso(description, level) values('Alta', 3);


-- aggiunta colonna priorita' flusso
alter table sviluppo_flussi add column id_priorita integer default 1;

-- aggiunta colonna note aggiornamenti priorita'
alter table sviluppo_flussi add column note_aggiornamenti_priorita text;

-- creazione tabella note -> flusso
-- Table: public.sviluppo_note_flusso
-- DROP TABLE IF EXISTS public.sviluppo_note_flusso;

CREATE TABLE IF NOT EXISTS public.sviluppo_note_flusso
(
    id serial,
    nota text COLLATE pg_catalog."default",
    id_flusso integer,
    id_utente integer,
    data_inserimento timestamp without time zone DEFAULT now(),
    data_cancellazione timestamp without time zone,
    CONSTRAINT sviluppo_note_flusso_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.sviluppo_note_flusso
    OWNER to postgres;

-- reset
-- reset permesso di Gestione Flussi (devdoc)    
delete from role_permission rp where rp.permission_id = (select p.permission_id from permission p where p.permission = 'devdoc');
delete from permission where permission = 'devdoc';

-- reset permesso di Modifica priorit� flusso (devdoc-priorita)
delete from role_permission rp where rp.permission_id = (select p.permission_id from permission p 
where p.permission = 'devdoc-priorita');
delete from permission where permission = 'devdoc-priorita';

-- reset permesso di aggiunta modulo VCE (devdoc-vce)
delete from role_permission rp where rp.permission_id = (select p.permission_id from permission p 
where p.permission = 'devdoc-vce');
delete from permission where permission = 'devdoc-vce';

-- creazione
-- creazione categoria permessi 'Gestione Flussi'
insert into permission_category(category, description, level, enabled, active, constant)
values('Gestione Flussi', 'Gestione Flussi e relativi moduli', 1, true, true, 1);

-- creazone permesso di Gestione Flussi (devdoc)
insert into permission(category_id, permission, permission_view, permission_edit, permission_delete, description)
values ((select category_id from permission_category where category = 'Gestione Flussi'),
		'devdoc', true, true, true, 'Abilita la Gestione Flussi');
		
-- creazione permesso di modifica priori� flusso (devdoc-priorita)
insert into permission(category_id, permission, permission_view, description)
values((select category_id from permission where permission = 'devdoc'),
		'devdoc-priorita', true, 'Abilita la modifica alla priorit� di un flusso');

-- creazione permesso relativo al modulo A (devdoc-mod-a)
insert into permission(category_id, permission, permission_view, permission_add, description)
values((select category_id from permission where permission = 'devdoc'),
		'devdoc-mod-a', true, true, 'Abilita vista/inserimento modulo A');
		
-- creazione permesso relativo al modulo B (devdoc-mod-b)
insert into permission(category_id, permission, permission_view, permission_add, description)
values((select category_id from permission where permission = 'devdoc'),
		'devdoc-mod-b', true, true, 'Abilita vista/inserimento modulo B');
		
-- creazione permesso relativo al modulo C (devdoc-mod-c)
insert into permission(category_id, permission, permission_view, permission_add, description)
values((select category_id from permission where permission = 'devdoc'),
		'devdoc-mod-c', true, true, 'Abilita vista/inserimento modulo C');
		
-- creazione permesso relativo al modulo CH (devdoc-mod-ch)
insert into permission(category_id, permission, permission_view, permission_add, description)
values((select category_id from permission where permission = 'devdoc'),
		'devdoc-mod-ch', true, true, 'Abilita vista/inserimento modulo CH');

-- creazione permesso relativo al modulo D (devdoc-mod-d)
insert into permission(category_id, permission, permission_view, permission_add, description)
values((select category_id from permission where permission = 'devdoc'),
		'devdoc-mod-d', true, true, 'Abilita vista/inserimento modulo D');
		
-- creazione permesso relativo al modulo VCE (devdoc-mod-vce)
insert into permission(category_id, permission, permission_view, permission_add, description)
values((select category_id from permission where permission = 'devdoc'),
		'devdoc-mod-vce', true, true, 'Abilita vista/inserimento modulo VCE');

-- settaggio permessi
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values
-- ORSA
((select role_id from role where role = 'ORSA'), (select permission_id from permission where permission = 'devdoc'), true, false, false, false),
((select role_id from role where role = 'ORSA'), (select permission_id from permission where permission = 'devdoc-mod-a'), true, false, false, false),
((select role_id from role where role = 'ORSA'), (select permission_id from permission where permission = 'devdoc-mod-b'), true, true, false, false),
((select role_id from role where role = 'ORSA'), (select permission_id from permission where permission = 'devdoc-mod-c'), true, true, false, false),
((select role_id from role where role = 'ORSA'), (select permission_id from permission where permission = 'devdoc-mod-ch'), true, true, false, false),
((select role_id from role where role = 'ORSA'), (select permission_id from permission where permission = 'devdoc-mod-d'), true, true, false, false),
-- HD livello 1
((select role_id from role where role = 'Amministratore Sistema HD I Livello'), (select permission_id from permission where permission = 'devdoc'), true, false, true, true),
((select role_id from role where role = 'Amministratore Sistema HD I Livello'), (select permission_id from permission where permission = 'devdoc-mod-a'), true, false, false, false),
((select role_id from role where role = 'Amministratore Sistema HD I Livello'), (select permission_id from permission where permission = 'devdoc-mod-b'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD I Livello'), (select permission_id from permission where permission = 'devdoc-mod-c'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD I Livello'), (select permission_id from permission where permission = 'devdoc-mod-ch'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD I Livello'), (select permission_id from permission where permission = 'devdoc-mod-d'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD I Livello'), (select permission_id from permission where permission = 'devdoc-mod-vce'), true, true, false, false),
-- HD livello 2
((select role_id from role where role = 'Amministratore Sistema HD II livello'), (select permission_id from permission where permission = 'devdoc'), true, false, true, true),
((select role_id from role where role = 'Amministratore Sistema HD II livello'), (select permission_id from permission where permission = 'devdoc-mod-a'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD II livello'), (select permission_id from permission where permission = 'devdoc-mod-b'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD II livello'), (select permission_id from permission where permission = 'devdoc-mod-c'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD II livello'), (select permission_id from permission where permission = 'devdoc-mod-ch'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD II livello'), (select permission_id from permission where permission = 'devdoc-mod-d'), true, true, false, false),
((select role_id from role where role = 'Amministratore Sistema HD II livello'), (select permission_id from permission where permission = 'devdoc-mod-vce'), true, true, false, false),
-- Responsabile Regione
((select role_id from role where role = 'Responsabile Regione'), (select permission_id from permission where permission = 'devdoc'), true, false, false, false),
((select role_id from role where role = 'Responsabile Regione'), (select permission_id from permission where permission = 'devdoc-mod-a'), true, false, false, false),
((select role_id from role where role = 'Responsabile Regione'), (select permission_id from permission where permission = 'devdoc-mod-b'), true, false, false, false),
((select role_id from role where role = 'Responsabile Regione'), (select permission_id from permission where permission = 'devdoc-mod-c'), true, false, false, false),
((select role_id from role where role = 'Responsabile Regione'), (select permission_id from permission where permission = 'devdoc-mod-ch'), true, false, false, false),
((select role_id from role where role = 'Responsabile Regione'), (select permission_id from permission where permission = 'devdoc-mod-d'), true, false, false, false),
((select role_id from role where role = 'Responsabile Regione'), (select permission_id from permission where permission = 'devdoc-priorita'), true, false, false, false);

-- creazione colonna 'note_hd' in 'sviluppo_flussi'
alter table sviluppo_flussi add column note_hd text;

-- trigger che tiene traccia delle operazioni effettuate sui flussi
create or replace function log_operazioni_flusso() returns trigger as $$
declare
	nuova_nota text := '';
begin
	
	if old.data_consegna is null and new.data_consegna is not null then -- si sta consegnando (chiudendo) un flusso
		nuova_nota := format(E'DATA: %s - OPERAZIONE: consegna - UTENTE: %s\n', now(), new.id_utente_consegna);
		new.note_hd := concat(nuova_nota, old.note_hd);
	elsif old.data_consegna is not null and new.data_consegna is null then -- si sta riaprendo un flusso da consegna
		nuova_nota := format(E'DATA: %s - OPERAZIONE: riapertura da consegna - UTENTE: %s\n', now(), new.id_utente_consegna);
		new.note_hd := concat(nuova_nota, old.note_hd);
	end if;
	
	if old.data_standby is null and new.data_standby is not null then -- si sta mettendo in standby un flusso
		nuova_nota := format(E'DATA: %s - OPERAZIONE: standby - UTENTE: %s\n', now(), new.id_utente_standby);
		new.note_hd := concat(nuova_nota, old.note_hd);
	elsif old.data_standby is not null and new.data_standby is null then -- si sta riaprendo un flusso da standby
		nuova_nota := format(E'DATA: %s - OPERAZIONE: riapertura da standby - UTENTE: %s\n', now(), new.id_utente_standby);
		new.note_hd := concat(nuova_nota, old.note_hd);
	end if;
	
	if old.data_annullamento is null and new.data_annullamento is not null then -- si sta annullando un flusso
		nuova_nota := format(E'DATA: %s - OPERAZIONE: annullamento - UTENTE: %s\n', now(), new.id_utente_annullamento);
		new.note_hd := concat(nuova_nota, old.note_hd);
	elsif old.data_annullamento is not null and new.data_annullamento is null then -- si sta riaprendo un flusso da annullamento
		nuova_nota := format(E'DATA: %s - OPERAZIONE: riapertura da annullamento - UTENTE: %s\n', now(), new.id_utente_annullamento);
		new.note_hd := concat(nuova_nota, old.note_hd);
	end if;
	
	return new;
end;
$$ language plpgsql;

create trigger log_operazioni_flusso before update on sviluppo_flussi for each row execute procedure log_operazioni_flusso();

-- nuove modifiche
-- settaggio permessi per Referente Regione
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit, role_delete) values
((select role_id from role where role = 'Referente Regione'), (select permission_id from permission where permission = 'devdoc'), true, false, false, false),
((select role_id from role where role = 'Referente Regione'), (select permission_id from permission where permission = 'devdoc-mod-a'), true, true, false, false),
((select role_id from role where role = 'Referente Regione'), (select permission_id from permission where permission = 'devdoc-mod-b'), true, false, false, false),
((select role_id from role where role = 'Referente Regione'), (select permission_id from permission where permission = 'devdoc-mod-c'), true, false, false, false),
((select role_id from role where role = 'Referente Regione'), (select permission_id from permission where permission = 'devdoc-mod-ch'), true, false, false, false),
((select role_id from role where role = 'Referente Regione'), (select permission_id from permission where permission = 'devdoc-mod-d'), true, false, false, false),
((select role_id from role where role = 'Referente Regione'), (select permission_id from permission where permission = 'devdoc-priorita'), true, false, false, false);



-- aggiornamenti del 17/01/2022
-- commentato perchè responsabile deve avere il permesso
--rimozione permesso devdoc-priorita al ruolo 'Responsabile Regione'
--delete from role_permission 
--where role_id = (select role_id from role where role = 'Responsabile Regione') and
--	  permission_id = (select permission_id from permission where permission = 'devdoc-priorita');

-- aggiornamento ruolo di 'di Loria' da ORSA a Referente Regione
-- commentato perchè viene già fatto a inizio script
--update access_ set role_id = (select role_id from role where role = 'Referente Regione') 
--where role_id = (select role_id from role where role = 'ORSA') and 
--	  contact_id in (select contact_id from contact_ where namelast ilike '%di loria%');
	  
-- aggiornamenti 4/02
-- aggiungo permesso di aggiunta mod a a responsavile regione e tolgo gli altri

update role_permission set role_add = true where permission_id in (select permission_id from permission where permission = 'devdoc-mod-a') and role_id = (select role_id from role where role = 'Responsabile Regione' );

update role_permission set role_add = false where permission_id in (select permission_id from permission where permission = 'devdoc-mod-b') and role_id = (select role_id from role where role = 'Responsabile Regione' );
update role_permission set role_add = false where permission_id in (select permission_id from permission where permission = 'devdoc-mod-c') and role_id = (select role_id from role where role = 'Responsabile Regione' );
update role_permission set role_add = false where permission_id in (select permission_id from permission where permission = 'devdoc-mod-d') and role_id = (select role_id from role where role = 'Responsabile Regione' );
update role_permission set role_add = false where permission_id in (select permission_id from permission where permission = 'devdoc-mod-ch') and role_id = (select role_id from role where role = 'Responsabile Regione' );


update role_permission set role_delete = true where permission_id in (select permission_id from permission where permission = 'devdoc') and role_id = (select role_id from role where role = 'Responsabile Regione' );
update role_permission set role_delete = true where permission_id in (select permission_id from permission where permission = 'devdoc') and role_id = (select role_id from role where role = 'Referente Regione' );



