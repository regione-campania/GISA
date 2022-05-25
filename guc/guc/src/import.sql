-- insert generiche per il framework

INSERT INTO permessi_funzione (id_funzione, nome, descrizione) VALUES ( 1,  'AMMINISTRAZIONE', NULL );
SELECT setval( 'permessi_funzione_id_funzione_seq', 2 );

INSERT INTO permessi_subfunzione (id_subfunzione, id_funzione, nome, descrizione) VALUES ( 1,  1, 'MAIN', NULL );
INSERT INTO permessi_subfunzione (id_subfunzione, id_funzione, nome, descrizione) VALUES ( 2,  1, 'RUOLI', NULL );
INSERT INTO permessi_subfunzione (id_subfunzione, id_funzione, nome, descrizione) VALUES ( 3,  1, 'UTENTI', NULL );
INSERT INTO permessi_subfunzione (id_subfunzione, id_funzione, nome, descrizione) VALUES ( 4,  1, 'FUNZIONI', NULL );
SELECT setval( 'permessi_subfunzione_id_subfunzione_seq', 5 );

INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 1,  1, 'MAIN', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 2,  2, 'MAIN', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 3,  3, 'MAIN', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 4,  4, 'MAIN', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 5,  4, 'ANAGRAFA', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 6,  2, 'PERMISSION EDIT', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 7,  3, 'ADD', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 8,  3, 'CAMBIO PASSWORD', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 9,  3, 'CAMBIO RUOLO', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 10, 3, 'DELETE', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 11, 3, 'EDIT', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 12, 3, 'ASSOCIAZIONE CLINICA', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 13, 4, 'PERMISSION LIST', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 14, 2, 'ADD', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 15, 2, 'DELETE', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 16, 2, 'EDIT', NULL );
SELECT setval( 'permessi_gui_id_gui_seq', 17 );

INSERT INTO permessi_ruoli (id, nome, descrizione) VALUES ( 1,  'Amministratore', 	'Amministra utenti, ruoli e permessi' );
SELECT setval( 'permessi_ruoli_id_seq', 2 );
SELECT setval( 'hibernate_sequence', 205 );

INSERT INTO secureobject (name) VALUES ('1');
INSERT INTO category (name) VALUES ('Amministratore');
INSERT INTO category_secureobject (categories_name, secureobjects_name) VALUES ( 'Amministratore', '1' );
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->MAIN->MAIN');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->RUOLI->MAIN');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->RUOLI->PERMISSION EDIT');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->UTENTI->MAIN');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->FUNZIONI->MAIN');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->FUNZIONI->ANAGRAFA');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->FUNZIONI->PERMISSION LIST');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->RUOLI->ADD');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->RUOLI->DELETE');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->RUOLI->EDIT');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->UTENTI->ADD');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->UTENTI->CAMBIO PASSWORD');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->UTENTI->CAMBIO RUOLO');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->UTENTI->DELETE');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->UTENTI->EDIT');
INSERT INTO subject (name) VALUES ('AMMINISTRAZIONE->UTENTI->ASSOCIAZIONE CLINICA');


INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 1 , 'Amministratore', NULL, 'AMMINISTRAZIONE->FUNZIONI->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 2 , 'Amministratore', NULL, 'AMMINISTRAZIONE->FUNZIONI->ANAGRAFA');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 3 , 'Amministratore', NULL, 'AMMINISTRAZIONE->UTENTI->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 4 , 'Amministratore', NULL, 'AMMINISTRAZIONE->RUOLI->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 5 , 'Amministratore', NULL, 'AMMINISTRAZIONE->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 6 , 'Amministratore', NULL, 'AMMINISTRAZIONE->RUOLI->PERMISSION EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 7 , 'Amministratore', NULL, 'AMMINISTRAZIONE->FUNZIONI->PERMISSION LIST');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 8 , 'Amministratore', NULL, 'AMMINISTRAZIONE->RUOLI->ADD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 9 , 'Amministratore', NULL, 'AMMINISTRAZIONE->RUOLI->DELETE');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 10, 'Amministratore', NULL, 'AMMINISTRAZIONE->RUOLI->EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 11, 'Amministratore', NULL, 'AMMINISTRAZIONE->RUOLI->PERMISSION EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 12, 'Amministratore', NULL, 'AMMINISTRAZIONE->UTENTI->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 13, 'Amministratore', NULL, 'AMMINISTRAZIONE->UTENTI->ADD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 14, 'Amministratore', NULL, 'AMMINISTRAZIONE->UTENTI->CAMBIO PASSWORD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 15, 'Amministratore', NULL, 'AMMINISTRAZIONE->UTENTI->CAMBIO RUOLO');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 16, 'Amministratore', NULL, 'AMMINISTRAZIONE->UTENTI->DELETE');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 17, 'Amministratore', NULL, 'AMMINISTRAZIONE->UTENTI->EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 18, 'Amministratore', NULL, 'AMMINISTRAZIONE->UTENTI->ASSOCIAZIONE CLINICA');


INSERT INTO permission (type, name) VALUES ('Group', 'rw');
INSERT INTO permission (type, name) VALUES ('Simple', 'r');
INSERT INTO permission (type, name) VALUES ('Simple', 'w');

INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 1, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 2, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 3, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 4, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 5, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 6, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 7, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 8, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 9, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 10, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 11, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 12, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 13, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 14, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 15, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 16, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 17, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 18, 'w');


INSERT INTO utenti( cognome, nome, username, password, email, entered_by, modified_by, telefono1,domanda_segreta,risposta_segreta, enabled ) VALUES ( 'System', 'Administrator', 'admin', md5('filippo'), 'info@paperino.it', -1, -1, '081 000000' ,'','', true);

---------- INSERT SPECIFICHE PER GUC ----------

insert into asl (id,nome) values(-1,'TUTTE')
insert into asl (id,nome) values(0,'NESSUNA')
insert into asl (id,nome) values(16,'Asl Fuori Regione')
insert into asl (id,nome) values(201,'AVELLINO')
insert into asl (id,nome) values(202,'BENEVENTO')
insert into asl (id,nome) values(203,'CASERTA')
insert into asl (id,nome) values(207,'SALERNO')
insert into asl (id,nome) values(204,'NAPOLI 1 CENTRO')
insert into asl (id,nome) values(205,'NAPOLI 2 NORD')
insert into asl (id,nome) values(206,'NAPOLI 3 SUD')
