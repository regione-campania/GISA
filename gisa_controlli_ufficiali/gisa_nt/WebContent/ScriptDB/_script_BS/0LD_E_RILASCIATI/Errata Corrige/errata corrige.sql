-- CHI: Bartolo Sansone	
-- COSA: Script errata corrige
-- QUANDO: 08/01/2018


CREATE TABLE richieste_errata_corrige(
id serial primary key,
riferimento_id integer,
riferimento_id_nome_tab text,
id_asl integer,
id_utente integer, 
data timestamp without time zone,
id_richieste_errata_corrige_lookup_motivo_correzione integer,
note text,
header_documento text);


CREATE TABLE richieste_errata_corrige_campi (
id serial primary key,
id_richieste_errata_corrige integer,
id_richieste_errata_corrige_lookup_info_da_modificare integer,
dato_errato text,
dato_corretto text);


CREATE TABLE richieste_errata_corrige_lookup_info_da_modificare
(
  code integer NOT NULL,
  description character varying(300) NOT NULL,
  short_description character varying(300),
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT richieste_errata_corrige_lookup_info_da_modificare_pkey PRIMARY KEY (code)
)

insert into richieste_errata_corrige_lookup_info_da_modificare (code, description) values (0, 'ALTRO');
insert into richieste_errata_corrige_lookup_info_da_modificare (code, description) values (1, 'IMPRESA PARTITA IVA');
insert into richieste_errata_corrige_lookup_info_da_modificare (code, description) values (2, 'IMPRESA RAPPRESENTANTE LEGALE');
insert into richieste_errata_corrige_lookup_info_da_modificare (code, description) values (3, 'LINEA PRODUTTIVA');
insert into richieste_errata_corrige_lookup_info_da_modificare (code, description) values (4, 'IMPRESA INDIRIZZO EMAIL');

CREATE TABLE richieste_errata_corrige_lookup_motivo_correzione
(
  code integer NOT NULL,
  description character varying(300) NOT NULL,
  short_description character varying(300),
  default_item boolean DEFAULT false,
  level integer DEFAULT 0,
  enabled boolean DEFAULT true,
  CONSTRAINT richieste_errata_corrige_lookup_motivo_correzione_pkey PRIMARY KEY (code)
)

insert into richieste_errata_corrige_lookup_motivo_correzione (code, description) values (0, 'ALTRO');
insert into richieste_errata_corrige_lookup_motivo_correzione (code, description) values (1, 'ERRORE OPERAZIONE UTENTE');
insert into richieste_errata_corrige_lookup_motivo_correzione (code, description) values (2, 'ERRORE DATI PREGRESSI');



insert into permission (category_id, permission, permission_view, permission_add, permission_edit, permission_delete, description, level, enabled)
values (29, 'richieste_errata_corrige', true, true, true, true, 'Gestione Richieste Errata Corrige', 2000, true);


insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (1, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, true);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (32, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, true);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (31, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, true);

insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (44, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (54, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (16, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (40, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (221, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (222, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (42, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (21, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (45, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (97, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (43, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (19, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (46, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (98, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (324, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (56, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
insert into role_permission(role_id, permission_id, role_view, role_add, role_edit) values (41, (select permission_id from permission where permission ilike 'richieste_errata_corrige'), true, true, false);
