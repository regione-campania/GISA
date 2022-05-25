--15/12/2014
--LOG OPERAZIONI UTENTE
ALTER TABLE utenti_operazioni_modifiche DROP CONSTRAINT "fk11470e6a961bfafd";
alter table utenti_operazioni_modifiche  add column nuovagestione boolean default false;
alter table utenti_operazioni_modifiche  add column userid integer;
alter table utenti_operazioni_modifiche  add column idcc integer;
alter table utenti_operazioni_modifiche  add column entered timestamp without time zone;
alter table utenti_operazioni_modifiche  add column urloperazione text;
alter table utenti_operazioni_modifiche  add column descrizioneoperazione text;

CHI: Rita Mele
COSA: Script di aggiornamento per la tabella utenti_super prendendo i dati dallo storico
QUANDO: 11/03/2015

alter table utenti_super add column last_login timestamp;

CHI: Rita Mele
COSA: Script di aggiornamento per la tabella utenti_super prendendo i dati dallo storico
QUANDO: 12/03/2015

--recuperati dal log ufficiale dello storico
select 'update utenti_super set last_login ='''||max||''' where username='''||username||''';'
from (
select distinct on (username) username, max(data)
from vam_storico_operazioni_utenti
group by data, username) a

--recuperati dalla tabella utenti operazioni
select 'update utenti_super set last_login ='''||max||''' where username='''||username||''';'
from (
select distinct on (u.username) u.username, max(u.modified)
from utenti_operazioni u
join utenti_super s on u.username = s.username
where s.last_login is null and s.trashed_date is null and s.enabled
group by u.modified, u.username) a



--Prototipo Richiesta istopatologico per LLPP
insert into permessi_subfunzione values(nextval('permessi_subfunzione_id_subfunzione_seq'), '', 'ADD_LLPP', 13);
insert into permessi_subfunzione values(nextval('permessi_subfunzione_id_subfunzione_seq'), '', 'LIST_LLPP', 13);
insert into permessi_gui values(nextval('permessi_gui_id_gui_seq'), '', 'MAIN', (select max(id_subfunzione) from permessi_subfunzione)-1);
insert into permessi_gui values(nextval('permessi_gui_id_gui_seq'), '', 'MAIN', (select max(id_subfunzione) from permessi_subfunzione));
INSERT INTO subject (name) VALUES ('RICHIESTA_ISTOPATOLOGICO->ADD_LLPP->MAIN');
INSERT INTO subject (name) VALUES ('RICHIESTA_ISTOPATOLOGICO->LIST_LLPP->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( nextval('capability_id_seq'), 'Veterinario Privato', NULL, 'RICHIESTA_ISTOPATOLOGICO->ADD_LLPP->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( nextval('capability_id_seq'), 'Veterinario Privato', NULL, 'RICHIESTA_ISTOPATOLOGICO->LIST_LLPP->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( (select max(id) from capability), 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( ( select max(id) from capability) - 1, 'w');
INSERT INTO permessi_ruoli (id, nome, descrizione) VALUES ( nextval('permessi_ruoli_id_seq'), 'Veterinario Privato', '' );
INSERT INTO permessi_ruoli_abilitati(id, id_ruolo, enabled) VALUES ((select max(id) from permessi_ruoli_abilitati)+1, (select max(id) from permessi_ruoli), true);
INSERT INTO category (name) values('Veterinario Privato');


--Modifiche Claudio: esame istopatologico per LLPP
update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);' where id_operazione = 2 and id_endpoint = 4;
update guc_endpoint_connector_config set sql = 'select * from dbi_cambia_profilo_utente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?);' where id_operazione = 3 and id_endpoint = 4;
update guc_endpoint_connector_config set sql = 'select * from dbi_insert_utente_guc(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?);' where id_operazione = 2 and id_endpoint = 6;

alter table utenti_super_ add column luogo text;
alter table utenti_super_ add column num_iscrizione_albo text;
alter table utenti_super_ add column sigla_provincia text;

alter table guc_utenti_ add column luogo_vam text;
alter table guc_utenti_ add column id_provincia_iscrizione_albo_vet_privato_vam integer;
alter table guc_utenti_ add column nr_iscrione_albo_vet_privato_vam text;

update lookup_detentori_sinantropi set enabled = true where id = 19;




--Numero Riferimento Mittente
alter table autopsia add column vincolo_unique_chiavi_autopsie boolean default false;

CREATE UNIQUE INDEX unique_chiavi_autopsie
  ON autopsia
  USING btree
  (numero_accettazione_sigla)
  WHERE trashed_date is null and numero_accettazione_sigla is not null and vincolo_unique_chiavi_autopsie is true;

--CAMBIARE A MANO IL DEFAULT A TRUE
  
select numero_accettazione_sigla, entered from autopsia
where numero_accettazione_sigla in 
(
  select numero_accettazione_sigla 
  from autopsia where entered >  and trashed_date is null and numero_accettazione_sigla <> '' and numero_accettazione_sigla is not null
  group by numero_accettazione_sigla
  having count(numero_accettazione_sigla) >1
)
order by numero_accettazione_sigla




