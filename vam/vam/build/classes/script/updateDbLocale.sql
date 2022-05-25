CHI: Rita Mele
COSA: Script di aggiornamento per la tabella utenti_super prendendo i dati dallo storico
QUANDO: 11/03/2015

alter table utenti_super add column last_login timestamp;

CHI: Rita Mele
COSA: Script di aggiornamento per la tabella utenti_super prendendo i dati dallo storico
QUANDO: 12/03/2015

--recuperati dal log
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
INSERT INTO category (name) values('Veterinario Privato');







