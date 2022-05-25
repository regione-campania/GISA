ALTER TABLE accettazione
   		ALTER COLUMN richiedente_altro TYPE text;
   		
INSERT INTO lookup_evento_apertura_cc(id, descrizione, enabled) values (1, 'Apertura cc ex novo', true);
INSERT INTO lookup_evento_apertura_cc(id, descrizione, enabled) values (2, 'Trasferimento ad altra clinica', true);
INSERT INTO lookup_evento_apertura_cc(id, descrizione, enabled) values (3, 'Rientro in clinica', true);
INSERT INTO lookup_evento_apertura_cc(id, descrizione, enabled) values (4, 'Apertura cc del morto', true);

   		
INSERT INTO permessi_funzione (id_funzione, nome, descrizione) VALUES ( 15,  'FASCICOLO_SANITARIO', NULL );
SELECT setval( 'permessi_funzione_id_funzione_seq', 16 );
INSERT INTO permessi_subfunzione (id_subfunzione, id_funzione, nome, descrizione) VALUES ( 72,  15, 'MAIN', NULL );
INSERT INTO permessi_subfunzione (id_subfunzione, id_funzione, nome, descrizione) VALUES ( 73,  15, 'DETAIL', NULL );
INSERT INTO permessi_subfunzione (id_subfunzione, id_funzione, nome, descrizione) VALUES ( 74,  15, 'LIST', NULL );
SELECT setval( 'permessi_subfunzione_id_subfunzione_seq', 75 );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 96, 72, 'MAIN', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 97, 73, 'MAIN', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( 98, 74, 'MAIN', NULL );
SELECT setval( 'permessi_gui_id_gui_seq', 99 );
INSERT INTO subject (name) VALUES ('FASCICOLO_SANITARIO->MAIN->MAIN');
INSERT INTO subject (name) VALUES ('FASCICOLO_SANITARIO->DETAIL->MAIN');
INSERT INTO subject (name) VALUES ('FASCICOLO_SANITARIO->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 221, 'Amministratore', NULL, 'FASCICOLO_SANITARIO->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 222, 'Amministratore', NULL, 'FASCICOLO_SANITARIO->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 223, 'Amministratore', NULL, 'FASCICOLO_SANITARIO->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 224, 'Ambulatorio - Veterinario', NULL, 'FASCICOLO_SANITARIO->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 225, 'Ambulatorio - Veterinario', NULL, 'FASCICOLO_SANITARIO->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 226, 'Ambulatorio - Veterinario', NULL, 'FASCICOLO_SANITARIO->DETAIL->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 221, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 222, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 223, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 224, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 225, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 226, 'w');


--Adattamento dell'esame necroscopico per gli uccelli
SELECT setval( 'lookup_autopsia_organi_id_seq', 54 );
insert into lookup_autopsia_organi( id, description, level_sde, enabled, tessuto, enabled_sde, level ) values( nextval('lookup_autopsia_organi_id_seq'), 'Piumaggio', 285, true, false , true, 495);
insert into lookup_autopsia_organi( id, description, level_sde, enabled, tessuto, enabled_sde, level ) values( nextval('lookup_autopsia_organi_id_seq'), 'Cloaca', 45, true, false , true, 496);

--permessi per il modulo statistiche e relativo assegnamento al ruolo regione
INSERT INTO permessi_funzione (id_funzione, nome, descrizione) VALUES ( nextval('permessi_funzione_id_funzione_seq'),  'STATISTICHE', NULL );
INSERT INTO permessi_subfunzione (id_subfunzione, id_funzione, nome, descrizione) VALUES ( nextval('permessi_subfunzione_id_subfunzione_seq'),  currval('permessi_funzione_id_funzione_seq'), 'MAIN', NULL );
INSERT INTO permessi_gui (id_gui, id_subfunzione, nome, descrizione) VALUES ( nextval('permessi_gui_id_gui_seq'), currval('permessi_subfunzione_id_subfunzione_seq'), 'MAIN', NULL );
INSERT INTO subject (name) VALUES ('STATISTICHE->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 227, 'Regione', NULL, 'STATISTICHE->MAIN->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 227, 'w');

--Esame Citologico
--Aggiornare lo script delle diagnosi quando saranno fornite quelle reali
INSERT INTO lookup_esame_citologico_tipo_prelievo(id, descrizione)VALUES (nextval('lookup_esame_citologico_tipo_prelievo_id_seq'), 'Ago aspirato');
INSERT INTO lookup_esame_citologico_tipo_prelievo(id, descrizione)VALUES (nextval('lookup_esame_citologico_tipo_prelievo_id_seq'), 'Impronta');
INSERT INTO lookup_esame_citologico_tipo_prelievo(id, descrizione)VALUES (nextval('lookup_esame_citologico_tipo_prelievo_id_seq'), 'Raschiato');
INSERT INTO lookup_esame_citologico_tipo_prelievo(id, descrizione)VALUES (nextval('lookup_esame_citologico_tipo_prelievo_id_seq'), 'Altro');
INSERT INTO lookup_esame_citologico_diagnosi(id, descrizione)VALUES (nextval('lookup_esame_citologico_diagnosi_id_seq'), 'Diagnosi 1');
INSERT INTO lookup_esame_citologico_diagnosi(id, descrizione)VALUES (nextval('lookup_esame_citologico_diagnosi_id_seq'), 'Diagnosi 2');
INSERT INTO lookup_esame_citologico_diagnosi(id, descrizione)VALUES (nextval('lookup_esame_citologico_diagnosi_id_seq'), 'Diagnosi 3');

--Modifiche richieste in data 25/09/2012
update lookup_evento_apertura_cc set descrizione = 'Apertura cartella necroscopica' where id = 4;
ALTER TABLE autopsia DROP COLUMN comune_ritrovamento;
ALTER TABLE autopsia ADD COLUMN comune_ritrovamento integer;
ALTER TABLE autopsia ADD CONSTRAINT fk55ce148a2afdab46 FOREIGN KEY (comune_ritrovamento)
      REFERENCES lookup_comuni (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

--Modifiche richieste da Rosato il 27/09/2012
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 34, 'Itteriche', 231, true );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 3, 34 );
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by  ) VALUES (21, 'IZSM Portici', 'IZSM-Portici', '0817865-111', 204, 378, 'Via Salute, 2', 'vamuserna1', 'sq51p3', 'vamuserna1', 'sq51p3', '0817763125', 'protocollo@cert.izsmportici.it', current_timestamp, 1 );
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 228, 'IZSM', NULL, 'TRASFERIMENTI->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 229, 'IZSM', NULL, 'TRASFERIMENTI->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 230, 'IZSM', NULL, 'TRASFERIMENTI->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 231, 'IZSM', NULL, 'TRASFERIMENTI->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 232, 'IZSM', NULL, 'TRASFERIMENTI->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 233, 'IZSM', NULL, 'CC->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 234, 'IZSM', NULL, 'CC->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 235, 'IZSM', NULL, 'CC->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 236, 'IZSM', NULL, 'CC->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 237, 'IZSM', NULL, 'CC->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 238, 'IZSM', NULL, 'CC->EDIT->MAIN');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (228, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (229, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (230, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (231, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (232, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (233, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (234, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (235, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (236, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (237, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (238, 'w');

--Modifiche richieste durante riunione del 03/10/2012 da Pompameo, Rosato ed Avallone
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by  ) VALUES (22, 'IZSM Avellino',  'IZSM-Avellino',  '0825755157', 201, 295, 'Via San Giovanni',                            'vamuserna1', 'sq51p3', 'vamuserna1', 'sq51p3', '0825755135', 'avellino@cert.izsmportici.it',  current_timestamp, 1 );
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by  ) VALUES (23, 'IZSM Benevento', 'IZSM-Benevento', '0824776040', 202, 54,  'Via Contrada S. Chirico',                     'vamuserna1', 'sq51p3', 'vamuserna1', 'sq51p3', '0824776907', 'benevento@cert.izsmportici.it', current_timestamp, 1 );
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by  ) VALUES (24, 'IZSM Caserta',   'IZSM-Caserta',   '0823388241', 203, 117, 'Via jervolino, 19',                           'vamuserna1', 'sq51p3', 'vamuserna1', 'sq51p3', '0823386766', 'caserta@cert.izsmportici.it',   current_timestamp, 1 );
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by  ) VALUES (25, 'IZSM Salerno',   'IZSM-Salerno',   '089301833',  207, 422, 'S.S.18 Via delle Calabrie, 27 - fraz.Fuorni', 'vamuserna1', 'sq51p3', 'vamuserna1', 'sq51p3', '089302699',  'benevento@cert.izsmportici.it', current_timestamp, 1 );
insert into lookup_autopsia_organi( id, description, level_sde, enabled, tessuto, enabled_sde, level ) values( nextval('lookup_autopsia_organi_id_seq'),  'Mammelle', 185, true, false , true, 65);
update lookup_operazioni_accettazione set effettuabile_fuori_asl = true where id in (1,2,4,5,6);
update lookup_operazioni_accettazione set effettuabile_fuori_asl = false where id = 3;
update lookup_operazioni_accettazione set effettuabile_fuori_asl_morto = effettuabile_fuori_asl;
update lookup_operazioni_accettazione set effettuabile_fuori_asl_morto = true where id = 12;
update autopsia set fenomeni_cadaverici = null;
ALTER TABLE autopsia DROP COLUMN fenomeni_cadaverici;
delete from lookup_autopsia_fenomeni_cadaverici;
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 1, 'Rigor Mortis', 						 	  	10, true, null, null  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 2, 'Alterazioni post-mortali dell''occhio', 	  	20, true, null, null  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 3, 'Macchie ipostatiche', 					 	  	30, true, null, null  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 4, 'Segni di putrefazione', 				 	  	40, true, null, null  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 5, 'Non presente', 						 	  	10, true,    1, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 6, 'Non completo', 						      	20, true,    1, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 7, 'Completo', 				                  	30, true,    1, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 8, 'In risoluzione', 						      	40, true,    1, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values( 9, 'Risolto', 								      	50, true,    1, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(10, 'opacamento corneale(velo di Winslow)',       	10, true,    2, false );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(11, 'infossamento globo oculare(segno di Louis)', 	20, true,    2, false );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(12, 'macchie scleroticali di Sommer', 			  	30, true,    2, false );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(13, 'assenti', 			  						  	10, true,    3, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(14, 'presenti I fase(migrazione)', 				  	20, true,    3, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(15, 'presenti II fase(fissazione)', 			  	30, true,    3, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(16, 'assenti', 			  						  	10, true,    4, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(17, 'stadio cromatico', 			  			  	20, true,    4, true  );
insert into lookup_autopsia_fenomeni_cadaverici( id, description, level, enabled, padre, scelta_singola) values(18, 'stadio enfisematoso', 			  			  	30, true,    4, true  );
INSERT INTO lookup_operazioni_accettazione_condizionate(id, enabled, operazione_da_fare, operazione_condizionante, operazione_condizionata)
VALUES (nextval('lookup_operazioni_accettazione_condizionate_id_seq'), true, 'enable', 3, 12);
update lookup_operazioni_accettazione set effettuabile_da_vivo = true;
update lookup_operazioni_accettazione set effettuabile_da_vivo = false where id = 12;

--Modifiche richieste durante riunione del 17/10/2012 da Pompameo
UPDATE lookup_operazioni_accettazione SET effettuabile_da_vivo=false WHERE id=13;
INSERT INTO lookup_operazioni_accettazione_condizionate(id, enabled, operazione_da_fare, operazione_condizionante, operazione_condizionata)
values (nextval('lookup_operazioni_accettazione_condizionate_id_seq'), true, 'enable', 3, 13);
UPDATE lookup_operazioni_accettazione SET description = 'Trasferimento' where id = 4;
INSERT INTO lookup_tipo_trasferimento(id, description, enabled) VALUES (nextval('lookup_tipo_trasferimento_id_seq'), 'di proprietà intra Asl', true);
INSERT INTO lookup_tipo_trasferimento(id, description, enabled) VALUES (nextval('lookup_tipo_trasferimento_id_seq'), 'di proprietà extra Asl', true);
INSERT INTO lookup_tipo_trasferimento(id, description, enabled) VALUES (nextval('lookup_tipo_trasferimento_id_seq'), 'di proprietà fuori regione', true);
INSERT INTO lookup_tipo_trasferimento(id, description, enabled) VALUES (nextval('lookup_tipo_trasferimento_id_seq'), 'di residenza', true);
insert into lookup_operazioni_accettazione
( description, inbdr, canina, felina, sinantropi, level, enabled, approfondimenti, 
approfondimento_diagnostico_medicina, richiesta_prelievi_malattie_infettive, 
alta_specialita_chirurgica, diagnostica_strumentale, 
effettuabile_fuori_asl, effettuabile_da_morto, effettuabile_da_vivo, effettuabile_fuori_asl_morto   ) 
values( 'Ricovero in canile', false, true, true, true, 120, true, false, false, false, false, false, true, false, true, true );
insert into lookup_operazioni_accettazione
( description, inbdr, canina, felina, sinantropi, level, enabled, approfondimenti, 
approfondimento_diagnostico_medicina, richiesta_prelievi_malattie_infettive, 
alta_specialita_chirurgica, diagnostica_strumentale, 
effettuabile_fuori_asl, effettuabile_da_morto, effettuabile_da_vivo, effettuabile_fuori_asl_morto   ) 
values( 'Incompatibilità ambientale', false, true, true, true, 125, true, false, false, false, false, false, true, false, true, true );
insert into lookup_operazioni_accettazione
( description, inbdr, canina, felina, sinantropi, level, enabled, approfondimenti, 
approfondimento_diagnostico_medicina, richiesta_prelievi_malattie_infettive, 
alta_specialita_chirurgica, diagnostica_strumentale, 
effettuabile_fuori_asl, effettuabile_da_morto, effettuabile_da_vivo, effettuabile_fuori_asl_morto  ) 
values( 'Altro', false, true, true, true, 130, true, false, false, false, false, false, true, false, true, true );
update lookup_operazioni_accettazione set effettuabile_fuori_asl = false where id in (4,5);
insert into lookup_operazioni_accettazione( description, inbdr, canina, felina, sinantropi, level, enabled, approfondimenti, 
approfondimento_diagnostico_medicina, richiesta_prelievi_malattie_infettive, alta_specialita_chirurgica, diagnostica_strumentale  ) 
values( 'Furto', true, true, true, false, 75, true, false, 
false, false, false, false );

--Richieste Rosato 14/11/2012
insert into lookup_autopsia_organi( id, description, level_sde, enabled, tessuto, enabled_sde, level ) values( nextval('lookup_autopsia_organi_id_seq'), 'Naso', 		  235, true, false , true, 510);
insert into lookup_autopsia_organi( id, description, level_sde, enabled, tessuto, enabled_sde, level ) values( nextval('lookup_autopsia_organi_id_seq'), 'Cavità nasali',  30, true, false , true, 520);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 1, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 2, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 4, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 5, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 6, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 55, 6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 1, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 2, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 4, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 5, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 6, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 56, 6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 1, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 2, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 4, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 5, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 6, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 57, 6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 1, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 2, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 4, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 5, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 6, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 58, 6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 1, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 2, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 4, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 5, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 6, 63, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES (nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, 59, 6, null, 1470);

--Altre Richieste
update lookup_operazioni_accettazione set effettuabile_fuori_asl = true, effettuabile_fuori_asl_morto = true, effettuabile_da_vivo = true where id = 50;

insert into lookup_operazioni_accettazione( description, inbdr, canina, felina, sinantropi, level, enabled, approfondimenti, approfondimento_diagnostico_medicina, richiesta_prelievi_malattie_infettive, alta_specialita_chirurgica, diagnostica_strumentale, effettuabile_fuori_asl, effettuabile_fuori_asl_morto, effettuabile_da_vivo  ) 
									values( 'Ricattura', true,    true,   true,      false,    77,    false,           false, 								false, 								   false, 					   false, 				    false,					 true, 						   true,				 true  );


insert into lookup_operazioni_accettazione( description, 					  inbdr, canina, felina, sinantropi, level, enabled, approfondimenti, approfondimento_diagnostico_medicina, richiesta_prelievi_malattie_infettive, alta_specialita_chirurgica, diagnostica_strumentale, effettuabile_fuori_asl, effettuabile_fuori_asl_morto, effettuabile_da_vivo ) 
values( 								'Ritrovamento(smarr. non denunciato)', true, true,     true,      false,    65,    true,           false,                                false,                                 false,                      false,                   false,                   true,                         true,                 true );


insert INTO lookup_operazioni_accettazione_condizionate (enabled,operazione_da_fare,operazione_condizionante,operazione_condizionata) VALUES  (true,'enable',51,47);
insert INTO lookup_operazioni_accettazione_condizionate (enabled,operazione_da_fare,operazione_condizionante,operazione_condizionata) VALUES  (true,'enable',51,48);
insert INTO lookup_operazioni_accettazione_condizionate (enabled,operazione_da_fare,operazione_condizionante,operazione_condizionata) VALUES  (true,'enable',51,49);


UPDATE  lookup_operazioni_accettazione SET enabled_default  = true ;
UPDATE  lookup_operazioni_accettazione SET enabled_default  = false WHERE id= 47 ;
UPDATE  lookup_operazioni_accettazione SET enabled_default  = false WHERE id= 48 ;
UPDATE  lookup_operazioni_accettazione SET enabled_default  = false WHERE id= 49 ;

--Creazione clinica virtuale per Università
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by  ) VALUES (26, 'Dip.Pat. e San. – Unina',   'DipPatSan–Unina',   '',  204, 313, '', 'vamuserna1', 'sq51p3', 'vamuserna1', 'sq51p3', '',  '', current_timestamp, 1 );
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 246, 'Università', NULL, 'TRASFERIMENTI->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 247, 'Università', NULL, 'TRASFERIMENTI->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 248, 'Università', NULL, 'TRASFERIMENTI->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 249, 'Università', NULL, 'TRASFERIMENTI->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 250, 'Università', NULL, 'TRASFERIMENTI->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 251, 'Università', NULL, 'CC->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 252, 'Università', NULL, 'CC->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 253, 'Università', NULL, 'CC->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 254, 'Università', NULL, 'CC->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 255, 'Università', NULL, 'CC->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 256, 'Università', NULL, 'CC->EDIT->MAIN');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (246, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (247, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (248, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (249, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (250, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (251, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (252, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (253, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (254, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (255, 'w');
INSERT INTO capability_permission(capabilities_id, permissions_name) VALUES (256, 'w');


--Richiesta Pompameo del 11/12/2012
UPDATE lookup_destinazione_animale SET description = 'Decesso' WHERE id = 2;
update lookup_operazioni_accettazione set enabled_default = true, effettuabile_da_morto = true where id = 49;

update lookup_operazioni_accettazione set effettuabile_fuori_asl_morto = false where id in(4,5);
update diario_clinico set temperatura = 'Non previsto' where temperatura = 'Non esaminato';
update lookup_diagnosi set cane = false where id = 55;
UPDATE lookup_eco_addome_tipo set nome='Rene destro'   where id=4;
UPDATE lookup_eco_addome_tipo set nome='Rene sinistro' where id=5;
ALTER TABLE terapia_assegnata ADD COLUMN stopped_by integer;
ALTER TABLE terapia_assegnata ADD CONSTRAINT fk516766c97686b24 FOREIGN KEY (stopped_by)
      REFERENCES utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
INSERT INTO lookup_eco_addome_referti(nome, tipo, id_eco_addome_tipo) VALUES ('Reni nei limiti di norma per dimensioni, morfologia ed ecostruttura. Non alterazioni parenchimali a focolaio. Cavità escretrici di calibro regolare.', 'N', 5);

INSERT INTO struttura_clinica(id, denominazione, clinica, tipo) VALUES (21, 'Sala Visite - Ambulatorio', 7, 1);
INSERT INTO struttura_clinica(id, denominazione, clinica, tipo) VALUES (22, 'Sala Degenze', 7, 3);
INSERT INTO struttura_clinica(id, denominazione, clinica, tipo) VALUES (23, 'Sala Operatoria', 7, 2);
INSERT INTO struttura_clinica(id, denominazione, clinica, tipo) VALUES (24, 'Box degenza', 30, 3);
INSERT INTO struttura_clinica(id, denominazione, clinica, tipo) VALUES (25, 'Box degenza', 31, 3);
INSERT INTO struttura_clinica(id, denominazione, clinica, tipo) VALUES (26, 'Sala Operatoria', 30, 2);
INSERT INTO struttura_clinica(id, denominazione, clinica, tipo) VALUES (27, 'Sala Operatoria', 31, 2);
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, 
                    remote_canina_password, remote_felina_username, remote_felina_password, fax, 
                    email, entered, entered_by ) 
VALUES (            27, 'UOV di S.Giuseppe Vesuviano', 'UOV-SGiusVes', '', 206, 436, '', 'vamuserna3', 
                    'sq51p3', 'vamuserna3', 'sq51p3', '', 
                    '', current_timestamp, 1 );
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, 
                    remote_canina_password, remote_felina_username, remote_felina_password, fax, 
                    email, entered, entered_by ) 
VALUES (            28, 'UOV di Capaccio', 'UOV-Capaccio', '', 207, 87, '', 'vamusersa', 
                    'sq51p3', 'vamusersa', 'sq51p3', '', 
                    '', current_timestamp, 1 );
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, 
                    remote_canina_password, remote_felina_username, remote_felina_password, fax, 
                    email, entered, entered_by ) 
VALUES (            29, 'UOV di Vallo della Lucania', 'UOV-Vallo', '', 207, 574, '', 'vamusersa', 
                    'sq51p3', 'vamusersa', 'sq51p3', '', 
                    '', current_timestamp, 1 );
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, 
                    remote_canina_password, remote_felina_username, remote_felina_password, fax, 
                    email, entered, entered_by ) 
VALUES (            30, 'Oasi Felix', 'Felix', '', 207, 368, '', 'vamusersa', 
                    'sq51p3', 'vamusersa', 'sq51p3', '', 
                    '', current_timestamp, 1 );
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, 
                    remote_canina_password, remote_felina_username, remote_felina_password, fax, 
                    email, entered, entered_by ) 
VALUES (            31, 'Rifugio comprensoriale per cani', 'Canile-TorreOrsaia', '', 207, 557, '', 'vamusersa', 
                    'sq51p3', 'vamusersa', 'sq51p3', '', 
                    '', current_timestamp, 1 );

--ASL Salerno ambito1
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (32, 'Ambulatorio ASL con degenza sanitaria di Cava de Tirreni', 'Amb-Cava de Tirreni', '', 207, 144, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (33, 'Ambulatorio ASL con degenza sanitaria di Maiori', 'Amb-Maiori', '', 207, 260, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (34, 'Ambulatorio presso canile municipale di Angri', 'CanileM-Angri', '', 207, 20, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (35, 'Ambulatorio presso canile municipale di Pagani', 'CanileM-Pagani', '', 207, 330, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (36, 'Ambulatorio presso canile municipale di Nocera Inferiore', 'CanileM-Nocera Inf.', '', 207, 314, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

--ASL Salerno ambito2

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (37, 'Ambulatorio con gabbie per degenza sanitaria-Distr.Salerno', 'Amb-D.Salerno', '', 207, 422, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (38, 'Ambulatorio con gabbie per degenza sanitaria-Distr.Pontecagnano', 'Amb-D.Pontecagnano', '', 207, 374, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (39, 'Ambulatorio funz non regolar-Distr.Giffoni Valle Piana', 'Amb-D.Giffoni V.P.', '', 207, 219, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (40, 'Ambulatorio aperto dal II sem. 2012-Distr.Battipaglia', 'Amb-D.Battipaglia', '', 207, 49, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (41, 'Ambulatorio riaperto dal II sem. 2012-Distr.Eboli', 'Amb-Distr.Eboli', '', 207, 186, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (42, 'Ambulatorio temporaneamente chiuso-Distr.Buccino', 'Amb-Distr.Buccino', '', 207, 63, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica (id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (43, 'UOV-di Piano di Sorrento', 'UOV-Piano di Sorrento', '', 206, 354, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );


update lookup_autopsia_organi set cani = true, gatti = true, uccelli = false;
update lookup_autopsia_organi set cani = false, gatti = false where description = 'Piumaggio' or description = 'Cloaca';

--Inserimento Organi
insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Seni infraorbitali', 450,true, false,false,530, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Cavo orale', 460,true, false,false,540, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Laringe e trachea', 470,true, false,false,550, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Sacchi aerei apprezzabili', 480,true, false,false,560, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Cuore', 490,true, false,false,570, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Fegato', 500,true, false,false,580, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Milza', 510,true, false,false,590, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Stomaco ghiandolare', 520,true, false,false,600, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Stomaco muscolare', 530,true, false,false,610, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Intestino', 540,true, false,false,620, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Ovario', 550,true, false,false,630, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Ovidutto', 560,true, false,false,640, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Testicoli', 570,true, false,false,650, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Reni', 580,true, false,false,660, false, false, true );

insert into lookup_autopsia_organi( id,description,level_sde, enabled, tessuto,  enabled_sde, level, cani,  gatti, uccelli ) 
values(nextval('lookup_autopsia_organi_id_seq'), 'Cranio', 590,true, false,false,670, false, false, true );

--Inserimento patologie(quello che esce sotto Esaminato)
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 35, 'Mucosa Anemica', 340, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 36, 'Mucosa Iperemica', 350, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 37, 'Mucosa Cianotica', 360, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 38, 'Presenza di essudato Sieroso', 370, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 39, 'Presenza di essudato Catarrale', 380, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 40, 'Presenza di essudato Emorragico', 390, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 41, 'Presenza di Rigurgito', 400, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 42, 'Presenza di Sangue Refluo', 410, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 43, 'Presenza di Traumi', 420, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 44, 'Presenza di Materiale caseoso', 430, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 45, 'Pseudomembrane fibrinose', 440, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 46, 'Toracici craniali Ispessimento', 450, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 47, 'Toracici craniali Essudato fibrinoso', 460, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 48, 'Toracici craniali Essudato caseoso', 470, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 49, 'Toracici craniali Noduli aspergillari', 480, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 50, 'Toracici craniali Parassiti', 490, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 51, 'Toracico addominale Ispessimento', 500, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 52, 'Toracico addominale Essudato fibrinoso', 510, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 53, 'Toracico addominale Essudato caseoso', 520, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 54, 'Toracico addominale Parassiti', 530, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 55, 'Sierosa Ispessimento', 540, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 56, 'Sierosa Essudato fibrinoso', 550, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 57, 'Emopericardio', 560, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 58, 'Periepate Ispessimento', 570, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 59, 'Periepate Essudato fibrinoso', 580, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 60, 'Margini Lievemente arrotondati', 590, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 61, 'Margini Notevolmente arrotondati', 600, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 62, 'Modificazioni di volume Lieve aumento', 610, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 63, 'Modificazioni di volume Epatomegalia', 620, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 64, 'Colore/aspetto gen. Congestione', 630, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 65, 'Colore/aspetto gen. Degenerazione', 640, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 66, 'Colore/aspetto gen. Steatosi', 650, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 67, 'Consistenza Diminuita', 660, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 68, 'Consistenza Aumentata', 670, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 69, 'Presenza di Emorragie puntiformi', 680, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 70, 'Presenza di Ematoma', 690, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 71, 'Presenza di Coaguli da rottura', 700, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 72, 'Presenza di Necrosi', 710, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 73, 'Presenza di Noduli', 720, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 74, 'Presenza di Noduli Miliariformi', 730, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 75, 'Margini Lievemente arrotondati', 740, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 76, 'Margini Notevolmente arrotondati', 750, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 77, 'Modificazione di volume Splenomegalia', 760, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 78, 'Modificazione di volume Ipotrofia', 770, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 79, 'Mucosa Emorragica', 780, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 80, 'Mucosa Ipertrofia ghiandolare', 790, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 81, 'Presenza di Emorragie puntiformi localizzate', 800, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 82, 'Presenza di Emorragie puntiformi diffuse', 810, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 83, 'Presenza di Ulcere', 820, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 84, 'Presenza di Parassiti', 830, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 85, 'Mucosa Coilina adesa', 840, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 86, 'Presenza di Bile', 850, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 87, 'Ispessimento parete', 860, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 88, 'Enterite Catarrale', 870, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 89, 'Enterite Emorragica', 880, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 90, 'Enterite Necrotico-emorragica', 890, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 91, 'Tratto interessato Duodeno', 900, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 92, 'Tratto interessato Digiuno', 910, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 93, 'Tratto interessato Ileo', 920, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 94, 'Tratto interessato Ciechi (se presenti)', 930, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 95, 'Tratto interessato Retto', 940, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 96, 'Parassiti Lieve presenza', 950, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 97, 'Parassiti Infestazione massiva', 960, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 98, 'Tonsille cecali Ipertrofiche', 970, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 99, 'Tonsille cecali Emorragiche', 980, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 100, 'Tonsille cecali Necrotico-emorragiche', 990, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 101, 'Sviluppo Immaturo', 1000, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 102, 'Sviluppo Maturo', 1010, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 103, 'Sviluppo Doppio', 1020, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 104, 'Iperemico', 1030, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 105, 'Emorragico', 1040, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 106, 'Necrotico-emorragico', 1050, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 107, 'Ovaro-peritonite', 1060, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 108, 'Aumento di volume', 1070, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 109, 'Degenerazione', 1080, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 110, 'Uricosi lieve', 1090, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 111, 'Uricosi generalizzata', 1100, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 112, 'Iperemia generalizzata', 1110, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 113, 'Ematomi', 1120, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 114, 'Cisti', 1130, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 115, 'Traumi', 1140, true );

--Associazione organi con patologie
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 35);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 36);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 37);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 38);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 39);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 40);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 41);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 42);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 61, 35);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 61, 36);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 61, 37);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 61, 43);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 61, 44);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 61, 45);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 35);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 36);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 37);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 38);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 39);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 40);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 41);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 42);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 46);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 47);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 48);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 49);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 50);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 51);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 52);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 53);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 54);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 64, 55);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 64, 56);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 64, 57);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 58);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 59);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 60);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 61);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 62);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 63);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 64);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 65);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 66);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 67);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 68);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 69);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 70);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 71);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 72);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 73);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 74);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 75);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 76);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 62);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 77);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 78);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 67);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 68);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 69);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 72);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 73);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 74);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 36);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 79);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 80);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 81);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 82);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 83);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 84);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 36);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 79);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 85);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 81);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 82);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 84);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 86);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 87);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 88);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 89);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 90);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 91);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 92);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 93);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 94);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 95);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 96);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 97);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 98);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 99);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 100);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 101);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 102);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 103);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 104);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 105);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 106);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 107);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 71, 101);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 71, 102);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 71, 104);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 71, 105);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 71, 106);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 101);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 102);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 73, 108);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 73, 109);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 73, 110);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 73, 111);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 74, 112);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 74, 113);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 74, 114);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 74, 115);
update lookup_autopsia_organi set enabled_sde = true where uccelli = true;
update lookup_esami_obiettivo_esito set description='BCS(Body Condition Status) 1' where description = 'BCS 1';
update lookup_esami_obiettivo_esito set description='BCS(Body Condition Status) 2' where description = 'BCS 2';
update lookup_esami_obiettivo_esito set description='BCS(Body Condition Status) 3' where description = 'BCS 3';
update lookup_esami_obiettivo_esito set description='BCS(Body Condition Status) 4' where description = 'BCS 4';
update lookup_esami_obiettivo_esito set description='BCS(Body Condition Status) 5' where description = 'BCS 5';

--Inserimento Cliniche ASL AVELLINO

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (44, 'Ambulatorio veterinario Monteforte Irpino', 'Amb-Monteforte Irpino', '', 201, 295, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (45, 'Ambulatorio veterinario Ariano Irpino', 'Amb-Ariano Irpino', '', 201, 25, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (46, 'Ambulatorio veterinario presso Canile "La Casa di Billy"', 'Amb.Vet.-"La Casa di Billy"', '', 201, 279, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

--Inserimento Cliniche ASL BENEVENTO
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (47, 'Ambulatorio Veterinario del canile comunale di Apice', 'Ambulatorio Veterinario-Apice', '', 202, 21, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (48, 'Unità Operativa Territoriale di Montesarchio', 'U.O.T.-Montesarchio', '', 202, 302, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (49, 'Unità Operativa Territoriale di Telese Terme', 'U.O.T.-Telese Terme', '', 202, 544, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (50, 'Unità Operativa Territoriale di Morcone', 'U.O.T.-Morcone', '', 202, 307, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (51, 'Unità Operativa Territoriale di Benevento', 'U.O.T.-Benevento', '', 202, 54, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );


--Inserimento cliniche CASERTA
INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (52, 'Ambulatorio Municipale di Caserta', 'Amb.Munic. Caserta', '', 203, 117, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (53, 'Ambulatorio Comunale di Maddaloni', 'Amb.Comun. Maddaloni', '', 203, 258, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (54, 'Ambulatorio Marcianise', 'Amb.Marcianise', '', 203, 263, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (55, 'Ambulatorio Comunale di Marcianise', 'Amb.Comun. Marcianise', '', 203, 263, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (56, 'Canile "La casa del Cane"- Distretto Alvignano', '', '', 203, 15, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (57, 'Ambulatorio canile Pontelatone', 'Amb.-Pontelatone', '', 203, 376, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (58, 'Ambulatorio- Distr. Piedimonte Matese', 'Amb.-Distr.Piedimonte Matese', '', 203, 356, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (59, 'Ambulatorio- Distr. Teano', 'Amb.-Distr. Teano', '', 203, 542, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (60, 'Ambulatorio-Distr. Mignano Montelungo', 'Amb.-Distr.Mignano Montelungo', '', 203, 277, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by ) 
VALUES (61, 'Ambulatorio Comunale S.Maria Capua Vetere', 'Amb.comun. S.Maria CV', '', 203, 477, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

update clinica set comune='68' where comune='376' and id='57'

update clinica set nome='Ambulatorio canile Pontelatone- Distr. Caiazzo' where nome='Ambulatorio canile Pontelatone' and id='57'

update clinica set nome_breve='Amb.Pontelatone-Distr.Caiazzo' where nome_breve='Amb.-Pontelatone' and id='57'

update clinica set nome='Amb.o Canile "La casa del Cane: Di Matteo Enza"-Dist. Alvignano' where nome='Amb.o Canile "La casa del Cane" di Matteo Enza-Dist. Alvignano' and id='56'

INSERT INTO lookup_razze(id,  cane,         description, enabled, enci, gatto, level)VALUES (597, true, 'SEGUGIO MAREMMANO',    true, 900, false,   1000);
INSERT INTO lookup_razze(id,  cane,         description, enabled, enci, gatto, level)VALUES (598, true, 'N.D.',    true, null, false,   1000);
INSERT INTO lookup_razze(id,  cane,         description, enabled, enci, gatto, level)VALUES (600, true, 'EGUGIO DELL''APPENNINO',    true, 901, false,   1000);

INSERT INTO lookup_mantelli(id, cane, description, enabled, gatto) VALUES (89, true, 'BIANCO TAN', true, false);

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (64, 'Amb. o Canile presso S.Angelo dei Lombardi', 'Canile S.Angelo dei L.', '', 201, 492, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (65, 'Amb. o Canile presso Avellino', 'Canile Avellino', '', 201, 38, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (66, 'Amb. o Canile presso Atripalda', 'Canile Atripalda', '', 201, 35, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (67, 'Amb. o Canile presso Baiano', 'Canile Baiano', '', 201, 44, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );


update lookup_operazioni_accettazione set description = 'Ricovero in struttura' where description = 'Ricovero in canile';

INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 257, 'Ambulatorio - Tecnico di supporto', NULL, 'HD->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 258, 'Ambulatorio - Tecnico di supporto', NULL, 'HD->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 259, 'Ambulatorio - Tecnico di supporto', NULL, 'HD->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 260, 'Ambulatorio - Tecnico di supporto', NULL, 'HD->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 261, 'Ambulatorio - Veterinario', NULL, 'HD->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 262, 'Ambulatorio - Veterinario', NULL, 'HD->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 263, 'Ambulatorio - Veterinario', NULL, 'HD->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 264, 'Ambulatorio - Veterinario', NULL, 'HD->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 265, 'Ambulatorio - Amministrativo', NULL, 'HD->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 266, 'Ambulatorio - Amministrativo', NULL, 'HD->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 267, 'Ambulatorio - Amministrativo', NULL, 'HD->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 268, 'Ambulatorio - Amministrativo', NULL, 'HD->EDIT->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 257, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 258, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 259, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 260, 'n');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 261, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 262, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 263, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 264, 'n');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 265, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 266, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 267, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 268, 'n');
delete from  capability_permission where capabilities_id = 260;
delete from  capability_permission where capabilities_id = 264;
delete from  capability_permission where capabilities_id = 268;
insert into lookup_razze( id, description, level, cane, gatto, enci, enabled ) values( 41, 'EGYPTIAN MAU', 1000, false,true,  '041', true );
UPDATE lookup_tickets set description = 'Miglioramenti consigliati' where id = 6;
update lookup_tipi_richiedente set enabled = false where id = 2;
update terapia_degenza set tipo = 'Farmacologica';
update lookup_operazioni_accettazione set obbligo_cc = true;
update lookup_operazioni_accettazione set obbligo_cc = false where inbdr = true;
update lookup_operazioni_accettazione set obbligo_cc = false where id = 8;
update lookup_operazioni_accettazione set obbligo_cc = false where id = 10;
update lookup_operazioni_accettazione set obbligo_cc = false where id = 13;
update lookup_operazioni_accettazione set obbligo_cc = false where richiesta_prelievi_malattie_infettive = true;

--Inserimento Ruolo Borsisti
INSERT INTO permessi_ruoli (id, nome, descrizione) VALUES ( 12, 'Borsisti', '' );
INSERT INTO category (name) VALUES ('Borsisti');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 257, 'Borsisti', NULL, 'CC->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 258, 'Borsisti', NULL, 'CC->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 259, 'Borsisti', NULL, 'CC->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 260, 'Borsisti', NULL, 'CC->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 261, 'Borsisti', NULL, 'CC->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 262, 'Borsisti', NULL, 'CC->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 263, 'Borsisti', NULL, 'TRASFERIMENTI->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 264, 'Borsisti', NULL, 'TRASFERIMENTI->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 265, 'Borsisti', NULL, 'TRASFERIMENTI->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 266, 'Borsisti', NULL, 'TRASFERIMENTI->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 267, 'Borsisti', NULL, 'TRASFERIMENTI->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 268, 'Borsisti', NULL, 'PERSONALE->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 269, 'Borsisti', NULL, 'PERSONALE->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 270, 'Borsisti', NULL, 'PERSONALE->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 271, 'Borsisti', NULL, 'PERSONALE->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 272, 'Borsisti', NULL, 'PERSONALE->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 273, 'Borsisti', NULL, 'PERSONALE->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 274, 'Borsisti', NULL, 'MAGAZZINO->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 275, 'Borsisti', NULL, 'MAGAZZINO->AS->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 276, 'Borsisti', NULL, 'MAGAZZINO->AS->ADD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 277, 'Borsisti', NULL, 'MAGAZZINO->AS->DELETE');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 278, 'Borsisti', NULL, 'MAGAZZINO->AS->DETAIL');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 279, 'Borsisti', NULL, 'MAGAZZINO->AS->EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 280, 'Borsisti', NULL, 'MAGAZZINO->AS->LIST');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 281, 'Borsisti', NULL, 'MAGAZZINO->MANGIMI->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 282, 'Borsisti', NULL, 'MAGAZZINO->MANGIMI->ADD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 283, 'Borsisti', NULL, 'MAGAZZINO->MANGIMI->DETAIL');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 284, 'Borsisti', NULL, 'MAGAZZINO->MANGIMI->DELETE');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 285, 'Borsisti', NULL, 'MAGAZZINO->MANGIMI->EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 286, 'Borsisti', NULL, 'MAGAZZINO->MANGIMI->LIST');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 287, 'Borsisti', NULL, 'MAGAZZINO->FARMACI->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 288, 'Borsisti', NULL, 'MAGAZZINO->FARMACI->ADD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 289, 'Borsisti', NULL, 'MAGAZZINO->FARMACI->DETAIL');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 290, 'Borsisti', NULL, 'MAGAZZINO->FARMACI->DELETE');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 291, 'Borsisti', NULL, 'MAGAZZINO->FARMACI->EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 292, 'Borsisti', NULL, 'MAGAZZINO->FARMACI->LIST');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 293, 'Borsisti', NULL, 'HD->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 294, 'Borsisti', NULL, 'REPORT->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 295, 'Borsisti', NULL, 'AGENDA->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 296, 'Borsisti', NULL, 'AGENDA->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 297, 'Borsisti', NULL, 'AGENDA->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 298, 'Borsisti', NULL, 'AGENDA->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 299, 'Borsisti', NULL, 'AGENDA->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 300, 'Borsisti', NULL, 'AGENDA->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 301, 'Borsisti', NULL, 'HD->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 302, 'Borsisti', NULL, 'FASCICOLO_SANITARIO->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 303, 'Borsisti', NULL, 'FASCICOLO_SANITARIO->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 304, 'Borsisti', NULL, 'FASCICOLO_SANITARIO->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 305, 'Borsisti', NULL, 'HD->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 306, 'Borsisti', NULL, 'HD->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 307, 'Borsisti', NULL, 'HD->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 308, 'Borsisti', NULL, 'HD->EDIT->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 257, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 258, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 259, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 260, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 261, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 262, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 263, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 264, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 265, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 266, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 267, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 268, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 269, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 270, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 271, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 272, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 273, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 274, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 275, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 276, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 277, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 278, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 279, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 280, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 281, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 282, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 283, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 284, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 285, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 286, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 287, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 288, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 289, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 290, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 291, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 292, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 293, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 294, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 295, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 296, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 297, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 298, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 299, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 300, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 301, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 302, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 303, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 304, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 305, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 306, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 307, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 308, 'w');

--Inserimento Ruolo Sinantropi
INSERT INTO permessi_ruoli (id, nome, descrizione) VALUES ( 13, 'Sinantropi', '' );
INSERT INTO category (name) VALUES ('Sinantropi');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 309, 'Sinantropi', NULL, 'BDR->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 310, 'Sinantropi', NULL, 'BDR->ADD->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 309, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 310, 'w');


update utenti set asl_referenza = 205 where username = 's.squitieri';

INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Agostino Vitale', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Dario Maguolo', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Pasquale Marrano', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Mariano Natale', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Gaetano Foria', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Salvatore Russo', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Salvatore Pace', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Antonello Diodato', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Giuseppina Di Gennaro', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Ciro Lendano', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Nando Lendano', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Carmine Fiorillo', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Raffaele Fiorillo', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Antonio Rosati', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Vincenzo Bottino', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Salvatore Buonocore', 204);
    INSERT INTO lookup_personale_interno(enabled, nominativo, asl)
    VALUES (true, 'Gaetano D''Ambria', 204);

delete from capability_permission where capabilities_id in (select id
from capability 
where subject_name ilike 'HD%');


--Ruolo Referente Asl
INSERT INTO permessi_ruoli (id, nome, descrizione) VALUES ( 14,  'Referente Asl', '' );
INSERT INTO category (name) VALUES ('Referente Asl');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 311, 'Referente Asl', NULL, 'ACCETTAZIONE->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 312, 'Referente Asl', NULL, 'ACCETTAZIONE->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 313, 'Referente Asl', NULL, 'ACCETTAZIONE->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 314, 'Referente Asl', NULL, 'ACCETTAZIONE->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 315, 'Referente Asl', NULL, 'ACCETTAZIONE->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 316, 'Referente Asl', NULL, 'ACCETTAZIONE->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 317, 'Referente Asl', NULL, 'BDR->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 318, 'Referente Asl', NULL, 'BDR->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 319, 'Referente Asl', NULL, 'BDR->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 320, 'Referente Asl', NULL, 'BDR->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 321, 'Referente Asl', NULL, 'BDR->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 322, 'Referente Asl', NULL, 'BDR->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 323, 'Referente Asl', NULL, 'CC->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 324, 'Referente Asl', NULL, 'CC->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 325, 'Referente Asl', NULL, 'CC->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 326, 'Referente Asl', NULL, 'CC->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 327, 'Referente Asl', NULL, 'CC->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 328, 'Referente Asl', NULL, 'CC->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 329, 'Referente Asl', NULL, 'TRASFERIMENTI->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 330, 'Referente Asl', NULL, 'TRASFERIMENTI->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 331, 'Referente Asl', NULL, 'TRASFERIMENTI->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 332, 'Referente Asl', NULL, 'TRASFERIMENTI->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 333, 'Referente Asl', NULL, 'TRASFERIMENTI->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 334, 'Referente Asl', NULL, 'PERSONALE->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 335, 'Referente Asl', NULL, 'PERSONALE->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 336, 'Referente Asl', NULL, 'PERSONALE->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 337, 'Referente Asl', NULL, 'PERSONALE->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 338, 'Referente Asl', NULL, 'PERSONALE->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 339, 'Referente Asl', NULL, 'PERSONALE->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 340, 'Referente Asl', NULL, 'MAGAZZINO->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 341, 'Referente Asl', NULL, 'MAGAZZINO->AS->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 342, 'Referente Asl', NULL, 'MAGAZZINO->AS->ADD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 343, 'Referente Asl', NULL, 'MAGAZZINO->AS->DELETE');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 344, 'Referente Asl', NULL, 'MAGAZZINO->AS->DETAIL');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 345, 'Referente Asl', NULL, 'MAGAZZINO->AS->EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 346, 'Referente Asl', NULL, 'MAGAZZINO->AS->LIST');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 347, 'Referente Asl', NULL, 'MAGAZZINO->MANGIMI->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 348, 'Referente Asl', NULL, 'MAGAZZINO->MANGIMI->ADD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 349, 'Referente Asl', NULL, 'MAGAZZINO->MANGIMI->DETAIL');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 350, 'Referente Asl', NULL, 'MAGAZZINO->MANGIMI->DELETE');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 351, 'Referente Asl', NULL, 'MAGAZZINO->MANGIMI->EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 352, 'Referente Asl', NULL, 'MAGAZZINO->MANGIMI->LIST');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 353, 'Referente Asl', NULL, 'MAGAZZINO->FARMACI->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 354, 'Referente Asl', NULL, 'MAGAZZINO->FARMACI->ADD');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 355, 'Referente Asl', NULL, 'MAGAZZINO->FARMACI->DETAIL');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 356, 'Referente Asl', NULL, 'MAGAZZINO->FARMACI->DELETE');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 357, 'Referente Asl', NULL, 'MAGAZZINO->FARMACI->EDIT');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 358, 'Referente Asl', NULL, 'MAGAZZINO->FARMACI->LIST');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 359, 'Referente Asl', NULL, 'HD->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 360, 'Referente Asl', NULL, 'REPORT->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 361, 'Referente Asl', NULL, 'AGENDA->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 362, 'Referente Asl', NULL, 'AGENDA->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 363, 'Referente Asl', NULL, 'AGENDA->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 364, 'Referente Asl', NULL, 'AGENDA->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 365, 'Referente Asl', NULL, 'AGENDA->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 366, 'Referente Asl', NULL, 'AGENDA->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 367, 'Referente Asl', NULL, 'HD->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 368, 'Referente Asl', NULL, 'FASCICOLO_SANITARIO->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 369, 'Referente Asl', NULL, 'FASCICOLO_SANITARIO->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 370, 'Referente Asl', NULL, 'FASCICOLO_SANITARIO->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 371, 'Referente Asl', NULL, 'HD->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 372, 'Referente Asl', NULL, 'HD->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 373, 'Referente Asl', NULL, 'HD->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 374, 'Referente Asl', NULL, 'HD->EDIT->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 311, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 312, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 313, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 314, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 315, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 316, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 317, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 318, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 319, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 320, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 321, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 322, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 323, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 324, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 325, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 326, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 327, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 328, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 329, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 330, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 331, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 332, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 333, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 334, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 335, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 336, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 337, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 338, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 339, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 340, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 341, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 342, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 343, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 344, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 345, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 346, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 347, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 348, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 349, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 350, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 351, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 352, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 353, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 354, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 355, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 356, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 357, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 358, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 359, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 360, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 361, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 362, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 363, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 364, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 365, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 366, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 367, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 368, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 369, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 370, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 371, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 372, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 373, 'w');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 374, 'w');

update permessi_ruoli set nome = 'Ambulatorio - Veterinario 2' where id = 12;
update category       set name = 'Ambulatorio - Veterinario 2' where name = 'Borsisti';
update capability set category_name = 'Ambulatorio - Veterinario 2' where category_name = 'Borsisti'

INSERT INTO permessi_ruoli (id, nome, descrizione) VALUES ( 15,  'Ambulatorio - Veterinario 3', '' );
INSERT INTO category (name) VALUES ('Ambulatorio - Veterinario 3');

INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 388, 'Ambulatorio - Veterinario 3', NULL, 'GESTIONE_ISTOPATOLOGICO->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 389, 'Ambulatorio - Veterinario 3', NULL, 'GESTIONE_ISTOPATOLOGICO->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 390, 'Ambulatorio - Veterinario 3', NULL, 'GESTIONE_ISTOPATOLOGICO->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 391, 'Ambulatorio - Veterinario 3', NULL, 'CC->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 392, 'Ambulatorio - Veterinario 3', NULL, 'TRASFERIMENTI->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 393, 'Ambulatorio - Veterinario 3', NULL, 'TRASFERIMENTI->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 394, 'Ambulatorio - Veterinario 3', NULL, 'TRASFERIMENTI->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 395, 'Ambulatorio - Veterinario 3', NULL, 'TRASFERIMENTI->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 396, 'Ambulatorio - Veterinario 3', NULL, 'TRASFERIMENTI->EDIT->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 397, 'Ambulatorio - Veterinario 3', NULL, 'CC->MAIN->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 398, 'Ambulatorio - Veterinario 3', NULL, 'CC->ADD->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 399, 'Ambulatorio - Veterinario 3', NULL, 'CC->DETAIL->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 400, 'Ambulatorio - Veterinario 3', NULL, 'CC->DELETE->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 401, 'Ambulatorio - Veterinario 3', NULL, 'CC->LIST->MAIN');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES ( 402, 'Ambulatorio - Veterinario 3', NULL, 'CC->EDIT->MAIN');
insert into capability_permission(capabilities_id, permissions_name) values (388, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (389, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (390, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (391, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (392, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (393, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (394, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (395, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (396, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (397, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (398, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (399, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (400, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (401, 'w');
insert into capability_permission(capabilities_id, permissions_name) values (402, 'w');

insert into lookup_destinazione_animale ( description, cane, gatto, sinantropo, level, enabled ) values( 'Trasferimento ad altra struttura' ,true, true, true, 6, true );


--Inserimento Clinica per Napoli3

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (68, 'U.O.V. 54 San Giorgio a Cremano', 'U.O.V. 54 San Giorgio a Cremano', '', 206, 431, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (69, 'Ambulatorio Veterinario Portici', 'Ambulatorio Veterinario Portici', '', 206, 378, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (70, 'U.O.V. 58 Gragnano', 'U.O.V. 58 Gragnano', '', 206, 225, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (71, 'U.O.V. 56 Torre Annunziata', 'U.O.V. 56 Torre Annunziata', '', 206, 554, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (72, 'U.O.C. Sanità Animale', 'U.O.C. Sanità Animale', '', 206, null, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (73, 'Ambulatorio Veterinario San Giuseppe Vesuviano', 'Amb-Vet San Giuseppe Vesuviano', '', 206, 436, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (74, 'U.O.V. 49 Nola', 'U.O.V. 49 Nola', '', 206, 316, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (75, 'U.O.V. 51 Sant''Anastasia', 'U.O.V. 51 Sant''Anastasia', '', 206, 485, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (76, 'U.O.V. 53 Castellammare di Stabia', 'U.O.V. 53 Castellammare di Stabia', '', 206, 132, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (77, 'U.O.V. 55 Ercolano', 'U.O.V. 55 Ercolano', '', 206, 187, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

update clinica set nome_breve='U.O.V. 59 Piano di Sorrento' where nome_breve='UOV-Piano di Sorrento'
and id='43'

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (78, 'U.O.V. 34 Portici', 'U.O.V. 34 Portici', '', 206, 378, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (79, 'U.O.V. 48 Marigliano', 'U.O.V. 48 Marigliano', '', 206, 265, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (80, 'U.O.V. 57 Torre del Greco', 'U.O.V. 57 Torre del Greco', '', 206, 555, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

INSERT INTO clinica(id, nome, nome_breve, telefono, asl, comune, indirizzo, remote_canina_username, remote_canina_password, remote_felina_username, remote_felina_password, fax, email, entered, entered_by )
VALUES (81, 'U.O.V. 58 Pompei', 'U.O.V. 58 Pompei', '', 206, 372, '', 'vamusersa', 'sq51p3', 'vamusersa', 'sq51p3', '', '', current_timestamp, 1 );

------------------------RITA MELE---------------------------------------------------------------------
--COSA: Creazione Tabella RX
--QUANDO: 26/03/2013

-- Table: rx
-- DROP TABLE rx;

CREATE TABLE rx
(
  id serial NOT NULL,
  data_esito date,
  data_richiesta date NOT NULL,
  note text,
  entered timestamp without time zone,
  modified timestamp without time zone,
  trashed_date timestamp without time zone,
  id_cartella_clinica integer,
  entered_by integer NOT NULL,
  modified_by integer,
  CONSTRAINT rx_pkey PRIMARY KEY (id ),
  CONSTRAINT fkeb966746bd84c91 FOREIGN KEY (modified_by)
      REFERENCES utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkeb9667497686b23 FOREIGN KEY (entered_by)
      REFERENCES utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkeb96674b6f4f274 FOREIGN KEY (id_cartella_clinica)
      REFERENCES cartella_clinica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE rx
  OWNER TO postgres;

--COSA:Creazione Tabella TAC
--QUANDO: 26/03/2013
  
-- Table: tac
-- DROP TABLE tac;

CREATE TABLE tac
(
  id serial NOT NULL,
  data_esito date,
  data_richiesta date NOT NULL,
  note text,
  entered timestamp without time zone,
  modified timestamp without time zone,
  trashed_date timestamp without time zone,
  id_cartella_clinica integer,
  entered_by integer NOT NULL,
  modified_by integer,
  CONSTRAINT tac_pkey PRIMARY KEY (id ),
  CONSTRAINT fkeb966746bd84c91 FOREIGN KEY (modified_by)
      REFERENCES utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkeb9667497686b23 FOREIGN KEY (entered_by)
      REFERENCES utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkeb96674b6f4f274 FOREIGN KEY (id_cartella_clinica)
      REFERENCES cartella_clinica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tac
  OWNER TO postgres;

--COSA: Creazione Tabella Tipo Intervento
--QUANDO: 26/03/2013


-- Table: tipo_intervento

-- DROP TABLE tipo_intervento;

CREATE TABLE tipo_intervento
(
  id serial NOT NULL,
  data_esito date,
  data_richiesta date NOT NULL,
  note text,
  entered timestamp without time zone,
  modified timestamp without time zone,
  trashed_date timestamp without time zone,
  id_cartella_clinica integer,
  entered_by integer NOT NULL,
  modified_by integer,
  CONSTRAINT tipo_intervento_pkey PRIMARY KEY (id ),
  CONSTRAINT fkeb966746bd84c91 FOREIGN KEY (modified_by)
      REFERENCES utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkeb9667497686b23 FOREIGN KEY (entered_by)
      REFERENCES utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkeb96674b6f4f274 FOREIGN KEY (id_cartella_clinica)
      REFERENCES cartella_clinica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tipo_intervento
  OWNER TO postgres;


--COSA: Creazione lookup Autopsia_sala_settoria
--QUANDO: 26/03/2013

CREATE TABLE lookup_autopsia_sala_settoria
(
  id serial NOT NULL,
  description character varying(64),
  enabled boolean,
  level integer,
  CONSTRAINT lookup_autopsia_sala_settoria_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_autopsia_sala_settoria
  OWNER TO postgres;

--COSA:Popolamento Lookup Autopsia Sala settoria
--QUANDO: 26/03/2013

INSERT INTO lookup_autopsia_sala_settoria(
            id, description, enabled, level)
    VALUES (1, 'Struttura propria', true, 10);
    
INSERT INTO lookup_autopsia_sala_settoria(
            id, description, enabled, level)
    VALUES (2, 'PROTEG Caivano', true, 20);
INSERT INTO lookup_autopsia_sala_settoria(
            id, description, enabled, level)
    VALUES (3, 'DOG PARK Ottaviano', true, 30);
    
    
----------------------- DANIELE ZANFARDINO
--COSA: FIX di "numero" e "progressivo" delle Cartelle cliniche inserite successivamente al 23/04/2013
--QUANDO: 20/05/2013
select * from cartella_clinica where entered >'2013-04-23 12:40:29.849' AND data_apertura >'31/12/2012' AND numero ilike '%POV-NA%' order by entered desc

update cartella_clinica  set numero = 'CC-POV-NA-2012-00001',progressivo=1 where id= 529;
update cartella_clinica  set numero = 'CC-POV-NA-2012-00002',progressivo=2 where id= 721
update cartella_clinica  set progressivo = progressivo + 367 where entered >'2013-04-23 12:40:29.849' AND data_apertura >'31/12/2012' AND numero ilike '%POV-NA%'
update cartella_clinica  set numero = 'CC-POV-NA-2013-00'||progressivo where entered >'2013-04-23 12:40:29.849' AND data_apertura >'31/12/2012' AND numero ilike '%POV-NA%'






----------STEFANO SQUITIERI-----------------------------------------------
--Modifica delle sottovoci poste nel necroscopico alla voce 'Stato di nutrizione'
--28/05/2013
delete from organi_patologieprevalenti where organo = 2 and patologia_prevalente in (14, 13,12,15 );


insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 116, 'BCS(Body Condition Status) 1', 340, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 117, 'BCS(Body Condition Status) 2', 350, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 118, 'BCS(Body Condition Status) 3', 360, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 119, 'BCS(Body Condition Status) 4', 370, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 120, 'BCS(Body Condition Status) 5', 380, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 121, 'Atrofia muscolare', 390, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 122, 'Atrofia dei crotafiti (accentuazione fossa sopraorbitaria)', 400, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 123, 'Ipotrofia', 410, true );

insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 116 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 117 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 118 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 119 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 120 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 121 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 122 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 123 );


------------ DANIELE ZANFARDINO ---------------------------------------
--SPOSTAMENTO DATI DEL RITROVAMENTO DEL RANDAGIO IN ANIMALE
-- 30/05/2013
ALTER TABLE animale ADD COLUMN indirizzo_ritrovamento character varying(255);
ALTER TABLE animale ADD COLUMN  note_ritrovamento text;
ALTER TABLE animale ADD COLUMN  provincia_ritrovamento character varying(255);
ALTER TABLE animale ADD COLUMN  comune_ritrovamento integer;
ALTER TABLE animale ADD CONSTRAINT fkccec31e95423a4fa FOREIGN KEY (comune_ritrovamento)
      REFERENCES lookup_comuni (id) MATCH SIMPLE;

  
-- TELEFONO E RESIDENZA DEL RICHIEDENTE
ALTER TABLE accettazione ADD COLUMN richiedente_residenza character varying(64);
ALTER TABLE accettazione ADD COLUMN richiedente_telefono character varying(16);


--31/05/2013
--SPOSTAMENTO DEI DATI SUL RITROVAMENTO DEL RANDAGIO DA AUTOPSIA AD ANIMALE

UPDATE animale as animal SET indirizzo_ritrovamento =  (select aut.indirizzo_ritrovamento from autopsia as aut join cartella_clinica as cc ON aut.id = cc.autopsia 
						LEFT JOIN accettazione as acc ON cc.accettazione = acc.id 
						LEFT JOIN animale AS an on acc.animale = an.id
						WHERE an.id = animal.id );


UPDATE animale as animal SET note_ritrovamento =  (select aut.note_ritrovamento from autopsia as aut join cartella_clinica as cc ON aut.id = cc.autopsia 
						LEFT JOIN accettazione as acc ON cc.accettazione = acc.id 
						LEFT JOIN animale AS an on acc.animale = an.id
						WHERE an.id = animal.id);

UPDATE animale as animal SET provincia_ritrovamento =  (select aut.provincia_ritrovamento from autopsia as aut join cartella_clinica as cc ON aut.id = cc.autopsia 
						LEFT JOIN accettazione as acc ON cc.accettazione = acc.id 
						LEFT JOIN animale AS an on acc.animale = an.id
						WHERE an.id = animal.id );

UPDATE animale as animal SET comune_ritrovamento =  (select aut.comune_ritrovamento from autopsia as aut join cartella_clinica as cc ON aut.id = cc.autopsia 
						LEFT JOIN accettazione as acc ON cc.accettazione = acc.id 
						LEFT JOIN animale AS an on acc.animale = an.id
						WHERE an.id = animal.id);

update animale set provincia_ritrovamento = '' where provincia_ritrovamento = 'X'




    



--03/06/2013
--ELIMINAZIONE DELLE COLONNE SUL RITROVAMENTO DA AUTOPSIA
ALTER TABLE autopsia DROP COLUMN indirizzo_ritrovamento;
ALTER TABLE autopsia DROP COLUMN note_ritrovamento;
ALTER TABLE autopsia DROP COLUMN provincia_ritrovamento;
ALTER TABLE autopsia DROP COLUMN comune_ritrovamento;

--05/06/2013
--mantello deve essere -1 quando non è stato vlaorizzato
update animale set mantello = -1 where mantello = 0 and deceduto_non_anagrafe

--05/06/2013
ALTER TABLE accettazione ADD COLUMN cc_vivo integer;
ALTER TABLE accettazione
  ADD CONSTRAINT fk2cf1339a16e5e45 FOREIGN KEY (cc_vivo)
      REFERENCES cartella_clinica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      
      
---Operatori multipli su esame necroscopico
CREATE TABLE autopsia_operatori
(
  autopsia integer NOT NULL,
  operatore integer NOT NULL,
  CONSTRAINT autopsia_operatori_pkey PRIMARY KEY (autopsia , operatore ),
  CONSTRAINT fk17e956d02afdab45 FOREIGN KEY (operatore)
      REFERENCES utenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk17e956d033f0fff7 FOREIGN KEY (autopsia)
      REFERENCES autopsia (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE autopsia_operatori
  OWNER TO postgres;


INSERT INTO autopsia_operatori ( autopsia, operatore )
SELECT  id, operatore
FROM    autopsia ;

CREATE TABLE tipo_intervento_operatori
(
  tipo_intervento integer NOT NULL,
  operatore integer NOT NULL,
  CONSTRAINT tipo_intervento_operatori_pkey PRIMARY KEY (tipo_intervento , operatore ),
  CONSTRAINT fk8b98006f2afdab45 FOREIGN KEY (operatore)
      REFERENCES utenti_super (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk8b98006f75c6b6de FOREIGN KEY (tipo_intervento)
      REFERENCES tipo_intervento (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tipo_intervento_operatori
  OWNER TO postgres;
  
INSERT INTO lookup_tickets(description, level, enabled) VALUES ('Altro',70,true);

alter table autopsia drop column operatore;

ALTER TABLE animale ALTER COLUMN note TYPE text;

--Stefano Squitieri - 07/08/2013: Modifiche per introdurre i piani IUV in V.A.M.
INSERT INTO lookup_operazioni_accettazione(
            id, alta_specialita_chirurgica, approfondimenti, approfondimento_diagnostico_medicina, 
            canina, description, diagnostica_strumentale, effettuabile_da_morto, 
            effettuabile_fuori_asl, enabled, felina, inbdr, level, richiesta_prelievi_malattie_infettive, 
            scelta_asl, sinantropi, effettuabile_fuori_asl_morto, effettuabile_da_vivo, 
            enabled_default, obbligo_cc)
    VALUES (nextval('lookup_operazioni_accettazione_id_seq'), false, false, false, 
            true, 'Attività Esterne', false, true, 
            true, false, true, false, 140, false, 
            false, false, true, true, 
            true, false);
  
CREATE TABLE lookup_accettazione_attivita_esterna
(
  id serial NOT NULL,
  description text,
  enabled boolean,
  CONSTRAINT lookup_accettazione_attivita_esterna_pkey PRIMARY KEY (id )
)
WITH ( OIDS=FALSE );

ALTER TABLE lookup_accettazione_attivita_esterna 
   OWNER TO postgres;

INSERT INTO lookup_accettazione_attivita_esterna(
            id, description, enabled)
    VALUES (nextval('lookup_accettazione_attivita_esterna_id_seq'), 'Identificazione,registrazione e destino delle carcasse di cani e gatti', true);
INSERT INTO lookup_accettazione_attivita_esterna(
            id, description, enabled)
    VALUES (nextval('lookup_accettazione_attivita_esterna_id_seq'), 'Pronto Soccorso', true);

ALTER TABLE accettazione ADD COLUMN indirizzo_attivita_esterna text;
ALTER TABLE accettazione ADD COLUMN attivita_esterna integer;
ALTER TABLE accettazione ADD COLUMN comune_attivita_esterna integer;

ALTER TABLE accettazione
  ADD CONSTRAINT fk2cf1339a1ef5b748 FOREIGN KEY (attivita_esterna)
      REFERENCES lookup_accettazione_attivita_esterna (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
ALTER TABLE accettazione
  ADD CONSTRAINT fk2cf1339abf8f2ffd FOREIGN KEY (comune_attivita_esterna)
      REFERENCES lookup_comuni (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
      
--Creazione viste per reportistica
CREATE OR REPLACE VIEW view_elenco_acc AS 
 SELECT DISTINCT acc.id AS id_accettazione, (((('ACC-'::text || cl.nome_breve::text) || '-'::text) || to_char(acc.data::timestamp with time zone, 'yyyy'::text)) || '-'::text) || to_char(acc.progressivo, 'FM00000'::text) AS numero_accettazione, acc.data AS data_accettazione, acc.note_altro AS note, a.identificativo AS microchip, u.username, u.nome, u.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM accettazione acc, animale a, accettazione_operazionirichieste aor, utenti u, clinica cl, lookup_asl asl
  WHERE acc.animale = a.id AND acc.id = aor.accettazione AND acc.entered_by = u.id AND u.clinica = cl.id AND cl.asl = asl.id AND acc.trashed_date IS NULL AND a.trashed_date IS NULL AND u.trashed_date IS NULL
  ORDER BY acc.data DESC;

ALTER TABLE view_elenco_acc
  OWNER TO postgres;

CREATE OR REPLACE VIEW view_elenco_acc_iscrizione_anagrafe AS 
 SELECT DISTINCT acc.id AS id_accettazione, (((('ACC-'::text || cl.nome_breve::text) || '-'::text) || to_char(acc.data::timestamp with time zone, 'yyyy'::text)) || '-'::text) || to_char(acc.progressivo, 'FM00000'::text) AS numero_accettazione, acc.data AS data_accettazione, acc.note_altro AS note, a.identificativo AS microchip, u.username, u.nome, u.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM accettazione acc, animale a, accettazione_operazionirichieste aor, utenti u, clinica cl, lookup_asl asl
  WHERE acc.animale = a.id AND acc.id = aor.accettazione AND (aor.operazione_richiesta = 1 OR aor.operazione_richiesta = 49 AND acc.note_altro ~~* '%isc%'::text) AND acc.entered_by = u.id AND u.clinica = cl.id AND cl.asl = asl.id AND acc.trashed_date IS NULL AND a.trashed_date IS NULL AND u.trashed_date IS NULL
  ORDER BY acc.data DESC;

ALTER TABLE view_elenco_acc_iscrizione_anagrafe
  OWNER TO postgres;


CREATE OR REPLACE VIEW view_elenco_acc_leishmaniosi AS 
 SELECT DISTINCT acc.id AS id_accettazione, (((('ACC-'::text || cl.nome_breve::text) || '-'::text) || to_char(acc.data::timestamp with time zone, 'yyyy'::text)) || '-'::text) || to_char(acc.progressivo, 'FM00000'::text) AS numero_accettazione, acc.data AS data_accettazione, acc.note_altro AS note, a.identificativo AS microchip, u.username, u.nome, u.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM accettazione acc, animale a, accettazione_operazionirichieste aor, utenti u, clinica cl, lookup_asl asl
  WHERE acc.animale = a.id AND acc.id = aor.accettazione AND aor.operazione_richiesta = 25 AND acc.entered_by = u.id AND u.clinica = cl.id AND cl.asl = asl.id AND acc.trashed_date IS NULL AND a.trashed_date IS NULL AND u.trashed_date IS NULL
  ORDER BY acc.data DESC;

ALTER TABLE view_elenco_acc_leishmaniosi
  OWNER TO postgres;

CREATE OR REPLACE VIEW view_elenco_acc_pronto_soccorso AS 
 SELECT DISTINCT acc.id AS id_accettazione, (((('ACC-'::text || cl.nome_breve::text) || '-'::text) || to_char(acc.data::timestamp with time zone, 'yyyy'::text)) || '-'::text) || to_char(acc.progressivo, 'FM00000'::text) AS numero_accettazione, acc.data AS data_accettazione, acc.note_altro AS note, a.identificativo AS microchip, u.username, u.nome, u.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM accettazione acc, animale a, accettazione_operazionirichieste aor, utenti u, clinica cl, lookup_asl asl
  WHERE acc.animale = a.id AND acc.id = aor.accettazione AND aor.operazione_richiesta = 11 AND acc.entered_by = u.id AND u.clinica = cl.id AND cl.asl = asl.id AND acc.trashed_date IS NULL AND a.trashed_date IS NULL AND u.trashed_date IS NULL
  ORDER BY acc.data DESC;

ALTER TABLE view_elenco_acc_pronto_soccorso
  OWNER TO postgres;

CREATE OR REPLACE VIEW view_elenco_acc_sterilizzazione AS 
 SELECT DISTINCT acc.id AS id_accettazione, (((('ACC-'::text || cl.nome_breve::text) || '-'::text) || to_char(acc.data::timestamp with time zone, 'yyyy'::text)) || '-'::text) || to_char(acc.progressivo, 'FM00000'::text) AS numero_accettazione, acc.data AS data_accettazione, acc.note_altro AS note, a.identificativo AS microchip, u.username, u.nome, u.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM accettazione acc, animale a, accettazione_operazionirichieste aor, utenti u, clinica cl, lookup_asl asl
  WHERE acc.animale = a.id AND acc.id = aor.accettazione AND aor.operazione_richiesta = 9 AND acc.entered_by = u.id AND u.clinica = cl.id AND cl.asl = asl.id AND acc.trashed_date IS NULL AND a.trashed_date IS NULL AND u.trashed_date IS NULL
  ORDER BY acc.data DESC;

ALTER TABLE view_elenco_acc_sterilizzazione
  OWNER TO postgres;

CREATE OR REPLACE VIEW view_elenco_animali_smaltimento AS 
 SELECT DISTINCT a.identificativo AS mc, a.data_smaltimento_carogna, a.ddt, a.ditta_autorizzata, 
        CASE
            WHEN a.deceduto_non_anagrafe = true THEN a.deceduto_non_anagrafe_data_morte::timestamp without time zone
            WHEN a.deceduto_non_anagrafe = false AND a.specie = 1 THEN ( SELECT t1.assigned_date
               FROM dblink('host=dbserverCanina dbname=canina user=postgres password=postgres'::text, ((('select t.assigned_date
										     from ticket t,
											  asset a 
										     where t.problem ilike ''%decesso%'' and
											   a.asset_id = t.link_asset_id and
											   (a.serial_number = '''::text || a.identificativo::text) || ''' or 
											    a.po_number = '''::text) || a.identificativo::text) || ''') and 
											   t.trashed_date is null and 
											   a.trashed_date is null 
									             limit 1'::text) t1(assigned_date timestamp without time zone))
            WHEN a.deceduto_non_anagrafe = false AND a.specie = 2 THEN ( SELECT t1.assigned_date
               FROM dblink('host=dbserverFelina dbname=felina user=postgres password=postgres'::text, ((('select t.assigned_date
										     from ticket t,
											  asset a 
										     where t.problem ilike ''%decesso%'' and
											   a.asset_id = t.link_asset_id and
											   (a.serial_number = '''::text || a.identificativo::text) || ''' or 
											    a.po_number = '''::text) || a.identificativo::text) || ''') and 
											   t.trashed_date is null and 
											   a.trashed_date is null 
									             limit 1'::text) t1(assigned_date timestamp without time zone))
            WHEN a.deceduto_non_anagrafe = false AND a.specie = 3 THEN ( SELECT s.data_decesso
               FROM sinantropo s
              WHERE s.mc::text = s.mc::text OR s.numero_automatico::text = s.mc::text OR s.numero_ufficiale::text = s.mc::text
             LIMIT 1)
            ELSE NULL::timestamp without time zone
        END AS data_decesso, upper(asl.description::text) AS asl
   FROM animale a, accettazione acc, utenti u, clinica c, lookup_asl asl
  WHERE a.trashed_date IS NULL AND a.id = acc.animale AND acc.entered_by = u.id AND u.clinica = c.id AND c.asl = asl.id AND a.data_smaltimento_carogna IS NOT NULL;

ALTER TABLE view_elenco_animali_smaltimento
  OWNER TO postgres;

CREATE OR REPLACE VIEW view_elenco_cc AS 
 SELECT cc.numero as cartella_clinica, 
     an.identificativo as microchip, 
                   cc.data_apertura, 
                   cc.data_chiusura, 
                        ut.username, 
                            ut.nome, 
                         ut.cognome,
           cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM cartella_clinica cc, utenti ut, clinica cl, lookup_asl asl, accettazione acc, animale an
  WHERE cc.trashed_date IS NULL AND ut.id = cc.entered_by AND ut.clinica = cl.id AND cl.asl = asl.id and cc.accettazione = acc.id and acc.animale = an.id;

ALTER TABLE view_elenco_cc
  OWNER TO postgres;

CREATE OR REPLACE VIEW view_elenco_necroscopie AS 
 SELECT aut.data_autopsia AS data_necroscopia, a.identificativo AS microchip, cc.numero AS cartella_clinica, u.username, u.nome, u.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM autopsia aut, animale a, cartella_clinica cc, accettazione acc, utenti u, clinica cl, lookup_asl asl
  WHERE aut.cartella_clinica = cc.id AND cc.accettazione = acc.id AND acc.animale = a.id AND aut.entered_by = u.id AND u.clinica = cl.id AND cl.asl = asl.id AND acc.trashed_date IS NULL AND aut.trashed_date IS NULL AND a.trashed_date IS NULL AND u.trashed_date IS NULL
  ORDER BY aut.data_autopsia DESC;

ALTER TABLE view_elenco_necroscopie
  OWNER TO postgres;

CREATE OR REPLACE VIEW view_elenco_cc_chiuse AS 
 SELECT cc.numero AS cartella_clinica, an.identificativo AS microchip, cc.data_apertura, cc.data_chiusura, ut.username, ut.nome, ut.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM cartella_clinica cc, utenti ut, clinica cl, lookup_asl asl, accettazione acc, animale an
  WHERE cc.trashed_date IS NULL AND 
        ut.id = cc.entered_by AND 
        ut.clinica = cl.id AND 
        cl.asl = asl.id AND 
        cc.accettazione = acc.id AND 
        acc.animale = an.id and 
        cc.data_chiusura is not null;

ALTER TABLE view_elenco_cc_chiuse
  OWNER TO postgres;

CREATE OR REPLACE VIEW view_elenco_cc_aperte AS 
 SELECT cc.numero AS cartella_clinica, an.identificativo AS microchip, cc.data_apertura, cc.data_chiusura, ut.username, ut.nome, ut.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM cartella_clinica cc, utenti ut, clinica cl, lookup_asl asl, accettazione acc, animale an
  WHERE cc.trashed_date IS NULL AND 
        ut.id = cc.entered_by AND 
        ut.clinica = cl.id AND 
        cl.asl = asl.id AND 
        cc.accettazione = acc.id AND 
        acc.animale = an.id and 
        cc.data_chiusura is null;

ALTER TABLE view_elenco_cc_aperte
  OWNER TO postgres;

  CREATE OR REPLACE VIEW view_elenco_cc_necroscopiche AS 
 SELECT cc.numero AS cartella_clinica, an.identificativo AS microchip, cc.data_apertura, cc.data_chiusura, ut.username, ut.nome, ut.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM cartella_clinica cc, utenti ut, clinica cl, lookup_asl asl, accettazione acc, animale an
  WHERE cc.trashed_date IS NULL AND 
        ut.id = cc.entered_by AND 
        ut.clinica = cl.id AND 
        cl.asl = asl.id AND 
        cc.accettazione = acc.id AND 
        acc.animale = an.id and 
        cc.cc_morto;

ALTER TABLE view_elenco_cc_necroscopiche
  OWNER TO postgres;

  CREATE OR REPLACE VIEW view_elenco_cc_cliniche AS 
 SELECT cc.numero AS cartella_clinica, an.identificativo AS microchip, cc.data_apertura, cc.data_chiusura, ut.username, ut.nome, ut.cognome, cl.nome AS clinica, upper(asl.description::text) AS asl
   FROM cartella_clinica cc, utenti ut, clinica cl, lookup_asl asl, accettazione acc, animale an
  WHERE cc.trashed_date IS NULL AND 
        ut.id = cc.entered_by AND 
        ut.clinica = cl.id AND 
        cl.asl = asl.id AND 
        cc.accettazione = acc.id AND 
        acc.animale = an.id and 
        cc.cc_morto = false;

ALTER TABLE view_elenco_cc_cliniche
  OWNER TO postgres;


--Creazione tabelle d'appoggio per reportistica(servono solo per ricavare lo script di create table da usare sul db gisa_report)
create table elenco_acc as select * from view_elenco_acc;
create table elenco_acc_iscrizione_anagrafe as select * from view_elenco_acc_iscrizione_anagrafe;
create table elenco_acc_leishmaniosi as select * from view_elenco_acc_leishmaniosi;
create table elenco_acc_pronto_soccorso as select * from view_elenco_acc_pronto_soccorso;
create table elenco_acc_sterilizzazione as select * from view_elenco_acc_sterilizzazione;
create table elenco_animali_smaltimento as select * from view_elenco_animali_smaltimento;
create table elenco_cc as select * from view_elenco_cc;
create table elenco_cc_chiuse as select * from view_elenco_cc_chiuse;
create table elenco_cc_aperte as select * from view_elenco_cc_aperte;
create table elenco_cc_necroscopiche as select * from view_elenco_cc_necroscopiche;
create table elenco_cc_cliniche as select * from view_elenco_cc_cliniche;
create table elenco_necroscopie as select * from view_elenco_necroscopie;



CREATE TABLE permessi_ruoli_abilitati
(
  id serial NOT NULL,
  id_ruolo integer NOT NULL,
  enabled boolean NOT NULL DEFAULT true,
  CONSTRAINT id PRIMARY KEY (id ),
  CONSTRAINT id_ruolo FOREIGN KEY (id_ruolo)
      REFERENCES permessi_ruoli (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE permessi_ruoli_abilitati
  OWNER TO postgres;
  

CREATE SCHEMA public_functions
  AUTHORIZATION postgres;

CREATE OR REPLACE FUNCTION public_functions.elimina_animale(microchip character varying)
  RETURNS integer AS
$BODY$DECLARE  
 eliminato integer; 
 id_animale integer;
 n_accettazioni_no_anag integer;
 accettazione_anag integer;
 n_cc integer;
 
BEGIN 



	select id into id_animale from animale where identificativo = microchip AND trashed_date is null;

	if (id_animale > 0) then
		select count(*) into n_accettazioni_no_anag
		from accettazione acc
		where acc.animale=id_animale and
		      acc.trashed_date is null and 
		      (
		       select count(*) 
		       from accettazione_operazionirichieste aor 
		       where aor.accettazione=acc.id and 
			     aor.operazione_richiesta<>1
		       )>0;

		select acc.id into accettazione_anag
		from accettazione acc
		where acc.animale=id_animale and
		      acc.trashed_date is null and 
		      (
		       select count(*) 
		       from accettazione_operazionirichieste aor 
		       where aor.accettazione=acc.id and 
		             aor.operazione_richiesta<>1
		       )=0 and
                      (
                       select count(*) 
                       from accettazione_operazionirichieste aor 
                       where aor.accettazione=acc.id and 
                             aor.operazione_richiesta=1
                      )>0;
		             
		select count(cc.*) into n_cc from cartella_clinica cc join accettazione on cc.accettazione = accettazione.id where animale  = id_animale AND cc.trashed_date is null;
		if (n_cc > 0 or n_accettazioni_no_anag > 0) then
			raise info 'Animale non eliminato perchè esistono dei riferimenti ad esso';
			eliminato = -2;
		else
			update animale set trashed_date = now() where id = id_animale;
			if(accettazione_anag is not null and accettazione_anag>0) then
				update accettazione acc set trashed_date = now() where id = accettazione_anag;
			end if;
			raise info 'Animale eliminato dal sistema';
			eliminato = 1;
		end if;
	else
		raise info 'Animale non presente nel sistema';
		eliminato = -1;
	end if;
	

	
	--raise info 'found % %', fascicolo_numero, ricoverato;


	return eliminato;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.elimina_animale(character varying)
  OWNER TO postgres;



  
-- Function: public_functions.is_ricoverato(character varying, timestamp without time zone)

-- DROP FUNCTION public_functions.is_ricoverato(character varying, timestamp without time zone);

CREATE OR REPLACE FUNCTION public_functions.is_ricoverato(microchip character varying, dataregistrazione timestamp without time zone)
  RETURNS boolean AS
$BODY$DECLARE  
 ricoverato boolean; 
 cursore refcursor; 
 fascicolo_numero integer;
BEGIN 



select count(fs.*) into fascicolo_numero
from cartella_clinica cc cross join fascicolo_sanitario fs cross join accettazione acc cross join animale an
where ( cc.trashed_date is null) and cc.fascicolo_sanitario=fs.id and cc.accettazione=acc.id and acc.animale=an.id and (fs.data_chiusura>=dataregistrazione or fs.data_chiusura is null) and fs.data_apertura<=dataregistrazione and an.identificativo = microchip; 

if (fascicolo_numero > 0) then
	ricoverato = true;
	else
	ricoverato = false;
	end if;
	
raise info 'found % %', fascicolo_numero, ricoverato;

return ricoverato;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.is_ricoverato(character varying, timestamp without time zone)
  OWNER TO postgres;


INSERT INTO lookup_operazioni_accettazione_condizionate(id, enabled, operazione_da_fare, operazione_condizionante, operazione_condizionata)
VALUES (nextval('lookup_operazioni_accettazione_condizionate_id_seq'), true, 'enable', 5, 6);

INSERT INTO lookup_operazioni_accettazione_condizionate(id, enabled, operazione_da_fare, operazione_condizionante, operazione_condizionata)
VALUES (nextval('lookup_operazioni_accettazione_condizionate_id_seq'), true, 'enable', 51, 2);
  
INSERT INTO lookup_personale_interno(enabled, nominativo, asl) VALUES (true, 'De Filippo Bruno', 204);

insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Caradriformi - Gabbiano', 145, true, true, false, false );

CREATE TABLE timbro_anagrafeanimali_storage
(
  id serial NOT NULL,
  specie integer,
  animale integer,
  microchip text,
  tipo text,
  version integer,
  nome_documento character varying(255),
  path character varying(255),
  data_creazione timestamp without time zone,
  utente_creazione character varying(255),
  utente_creazione_id integer,
  utente_creazione_ip character varying(255),
  path_server character varying(255),
  md5 character varying(255),
  letto integer NOT NULL DEFAULT 1,
  data_ultima_lettura timestamp without time zone,
  CONSTRAINT id_anagrafeanimali_documento PRIMARY KEY (id ),
  CONSTRAINT idanimale_fk FOREIGN KEY (animale)
      REFERENCES animale (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT idspecie_fk FOREIGN KEY (specie)
      REFERENCES lookup_specie (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE timbro_anagrafeanimali_storage
  OWNER TO postgres;
  
--Modifiche per sinantropi
CREATE TABLE lookup_sinantropi_eta
(
  id serial NOT NULL,
  description text,
  CONSTRAINT lookup_sinantropi_eta_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE lookup_sinantropi_eta
  OWNER TO postgres;


insert into lookup_sinantropi_eta values(nextval('lookup_sinantropi_eta_id_seq'),'Nidiaceo/Cucciolo');
insert into lookup_sinantropi_eta values(nextval('lookup_sinantropi_eta_id_seq'),'Subadulto');
insert into lookup_sinantropi_eta values(nextval('lookup_sinantropi_eta_id_seq'),'Adulto');
ALTER TABLE sinantropo ADD COLUMN eta integer;
ALTER TABLE sinantropo
  ADD CONSTRAINT fkfd5a4f8b7d2bece4 FOREIGN KEY (eta)
      REFERENCES lookup_sinantropi_eta (id) ;

ALTER TABLE animale ADD COLUMN eta integer;
ALTER TABLE animale
  ADD CONSTRAINT fkfd5a4f8b7d2bece9 FOREIGN KEY (eta)
      REFERENCES lookup_sinantropi_eta (id) ;

      
update lookup_detentori_sinantropi set description = 'CRAS presso POV – Asl Napoli 1' where description ilike '%Presidio Ospedaliero Veterinario%';
update sinantropo set last_operation = 'RINVENIMENTO' where last_operation = 'CATTURA';
update sinantropo set last_operation = 'RILASCIO' where last_operation = 'REIMMISSIONE';

insert into lookup_tipi_richiedente( description, level, enabled, forza_pubblica ) values( 'Polizia provinciale', 120, true, true );
insert into lookup_tipi_richiedente( description, level, enabled, forza_pubblica ) values( 'Polizia di Stato', 130, true, true );
insert into lookup_tipi_richiedente( description, level, enabled, forza_pubblica ) values( 'Guardia di Finanza', 140, true, true );
update lookup_tipi_richiedente set enabled = false where id = 9;
update accettazione set richiedente_tipo = 6 where richiedente_tipo = 9;
update lookup_tipi_struttura set description = 'Isolamento' where description = 'Quarantena';
update lookup_tipi_struttura set description = 'Box post-operatorio' where description = 'Box degenza';
update lookup_destinazione_animale set sinantropo = false where id = 3;
ALTER TABLE lookup_destinazione_animale ADD COLUMN description_sinantropo text;
update lookup_destinazione_animale set description_sinantropo = description;
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91520, 1, null, 'Rachitismo', 110, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91521, 1, null, 'Amputazione', 120, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91522, 1, null, 'Arto anteriore/superiore dx', 130, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91523, 1, null, 'Arto anteriore/superiore sin', 140, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91524, 1, null, 'Arto posteriore/inferiore sin', 150, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91525, 1, null, 'Arto posteriore/inferiore destro', 160, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91526, 4, null, 'Piumaggio arruffato', 460, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91528, 5, null, 'Penne remiganti lesionate primarie', 640, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91529, 5, null, 'Penne remiganti lesionate secondarie', 650, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91530, 5, null, 'Penne remiganti lesionate terziarie', 660, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91531, 5, null, 'Penne timoniere lesionate', 670, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91532, 5, null, 'Taglio remiganti primarie', 680, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91533, 5, null, 'Taglio remiganti secondarie', 690, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91534, 5, null, 'Taglio remiganti terziarie', 700, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91535, 5, null, 'Taglio timoniere', 710, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91536, 5, null, 'Ipercheratosi delle parti glabre', 720, true );
insert into lookup_esame_obiettivo_esito( id, tipo_esame, superesito, description, level, enabled ) values( 91537, 11, null, 'Stasi del gozzo', 1690, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 125, 'Presenza di essudato Caseoso', 395, true );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 124);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 124);
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 126, 'Toracici caudali Ispessimento', 540, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 127, 'Toracici caudali Essudato fibrinoso', 550, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 128, 'Toracici caudali Essudato caseoso', 560, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 129, 'Toracici caudali Noduli aspergillari', 570, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 130, 'Toracici caudali Parassiti', 580, true );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 126);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 127);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 128);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 129);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 130);
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 134, 'Enterite Acquosa', 900, true );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 134);
ALTER TABLE sinantropo ADD COLUMN codice_ispra character varying(255);
ALTER TABLE sinantropo_reimmissioni ADD COLUMN codice_ispra character varying(255);
ALTER TABLE struttura_clinica ADD COLUMN cane boolean;
ALTER TABLE struttura_clinica ADD COLUMN gatto boolean;
ALTER TABLE struttura_clinica ADD COLUMN sinantropo boolean;
update struttura_clinica set cane = true, gatto = true, sinantropo = true where denominazione != 'Tunnel di volo' and denominazione != 'Voliera';
update struttura_clinica set cane = false, gatto = false, sinantropo = true where denominazione = 'Tunnel di volo' or denominazione = 'Voliera';
update struttura_clinica set denominazione = 'Isolamento' where denominazione = 'Quarantena';
update struttura_clinica set denominazione = 'Box post-operatorio' where denominazione = 'Box degenza';
CREATE TABLE lookup_ferite
(
  id serial NOT NULL,
  description character varying(128),
  enabled boolean,
  level integer,
  CONSTRAINT lookup_ferite_pkey PRIMARY KEY (id )
)
WITH (
  OIDS=FALSE
);

ALTER TABLE lookup_ferite
  OWNER TO postgres;

CREATE TABLE animali_ferite
(
  ferite integer NOT NULL,
  cc integer NOT NULL,
  CONSTRAINT animali_ferite_pkey PRIMARY KEY (cc , ferite ),
  CONSTRAINT fk32ba3c55322ccf23 FOREIGN KEY (ferite)
      REFERENCES lookup_ferite (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk32ba3c55de03c57a FOREIGN KEY (cc)
      REFERENCES cartella_clinica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE animali_ferite
  OWNER TO postgres;

insert into lookup_ferite( description, level, enabled ) values( 'Proiettile', 	10, true );
insert into lookup_ferite( description, level, enabled ) values( 'Pallino', 	20, true );
insert into lookup_ferite( description, level, enabled ) values( 'Piombino', 	30, true );



--Aggiornamento Genere - Famiglia Sinantropi
--Disabilito i non usati finora
update lookup_specie_sinantropi set enabled = false where id in (
select id from lookup_specie_sinantropi s where uccello and (select count(*) from sinantropo where specie = s.id) = 0);

--Inserimento delle nuove specie
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Acquila minore - Accipitridi', 10, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Aquila minore - Accipitridi', 10, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Aquila reale - Accipitridi', 20, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Albanella minore - Accipitridi', 30, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Albanella reale - Accipitridi', 40, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Astore - Accipitridi', 50, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Biancone - Accipitridi', 60, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Falco di palude - Accipitridi', 70, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Nibbio reale - Accipitridi', 80, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Poiana calzata - Accipitridi', 90, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Poiana codabianca - Accipitridi', 100, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Allodola comune - Alaudidi', 110, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Allodola gola gialla - Alaudidi', 120, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Calandrella europea - Alaudidi', 130, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cappellaccia - Alaudidi', 140, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Tottavilla - Alaudidi', 150, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Martin pescatore - Alcedinidi', 160, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gazza marina - Alcidi', 170, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Alzavola - Anatidi', 180, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cigno reale - Anatidi', 190, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cigno selvatico - Anatidi', 200, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Codone - Anatidi', 210, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Marzaiola - Anatidi', 220, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Mestolone - Anatidi', 230, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Moretta - Anatidi', 240, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Moretta codona - Anatidi', 250, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Moretta grigia - Anatidi', 260, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Moretta tabaccata - Anatidi', 270, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Moriglione europeo - Anatidi', 280, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Oca granaiola - Anatidi', 290, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Oca lomardella - Anatidi', 300, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Oca selvatica - Anatidi', 310, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pescaiola - Anatidi', 320, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Quattrocchi - Anatidi', 330, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Smergo maggiore - Anatidi', 340, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Smergo minore - Anatidi', 350, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Svasso - Anatidi', 360, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Fistione - Anatidi', 370, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Fistione turco - Anatidi', 380, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Orco marino - Anatidi', 390, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Volpoca - Anatidi', 400, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Rondone alpino - Apodidi', 410, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Rondone pallido - Apodidi', 420, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Airone rosso - Ardeidi', 430, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Garzetta - Ardeidi', 440, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Nitticora - Ardeidi', 450, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Tarabusino europeo - Ardeidi', 460, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Beccofrusone - Bombicillidi', 470, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Occhione comune - Burinidi', 480, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Succiacapre europeo - Caprimulgidi', 490, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Corriere piccolo - Caradriidi', 500, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Fratino - Caradriidi', 510, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pavoncella - Caradriidi', 520, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piviere dorato - Caradriidi', 530, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piviere tortolino - Caradriidi', 540, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pivieressa - Caradriidi', 550, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Corriere grosso - Caradriidi', 560, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Rampichino - Cetriidi', 570, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cicogna bianca - Ciconidi ', 580, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cicogna nera  - Ciconidi ', 590, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Merlo acquaiolo - Cinclidi', 600, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Colombaccio - Columbidi', 610, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Colombella - Columbidi', 620, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piccione selvatico - Columbidi', 630, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Tortora dal collare orientale - Columbidi', 640, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Ghiandaia marina - Coraciidi', 650, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cornacchia nera - Corvidi', 660, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Ghiandaia  - Corvidi', 670, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Nocciolaia - Corvidi', 680, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Taccola - Corvidi', 690, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cuculo comune - Cuculidi', 700, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Beccaccia di mare europea - Ematopodidi', 710, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Migliarino di palude - Emberizidi', 720, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Zigolo giallo - Emverizidi', 730, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Zigolo nero - Emverizidi', 740, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Zigolo testa nera - Emverizidi', 750, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Falaropo becco largo - Falaropodidi', 760, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Falaropo becco sottile - Falaropodidi', 770, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Falco cuculo - Falconidi', 780, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Lodolaio comune - Falconidi', 790, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Smeriglio - Falconidi', 800, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Grillaio - Falconidi', 810, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Coturnice - Fasianidi', 820, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Fagiano comune - Fasianidi', 830, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pernice rossa - Fasianidi', 840, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Quaglia comune - Fasianidi', 850, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Starna - Fasianidi', 860, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Ciuffolotto - Fringillidi', 870, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Crociere - Fringillidi', 880, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Fanello - Fringillidi', 890, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Frosone - Fringillidi', 900, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Lucherino - Fringillidi', 910, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Peppola - Fringillidi', 920, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Venturone - Fringillidi', 930, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Verdone - Fringillidi', 940, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Verzellino - Fringillidi', 950, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pernice di mare comune - Glareolidi', 960, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gallina prataiola - Gruidi', 970, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gru cenerina - Gruidi', 980, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Otarda comune - Gruidi', 990, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Balestruccio - Irundinidi', 1000, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Topino - Irundinidi', 1110, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Averla capirossa - Laniidi', 1120, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Averla cenerina - Laniidi', 1130, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Averla maggiore  - Laniidi', 1140, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Averla piccola - Laniidi', 1150, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Beccapesci - Laridi', 1160, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Fraticello - Laridi', 1170, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gabbianello - Laridi', 1180, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gabbiano corso - Laridi', 1190, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gabbiano corallino - Laridi', 1200, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gabbiano glauco - Laridi', 1210, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gabbiano reale - Laridi', 1220, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gabbiano tridattilo - Laridi', 1230, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gabbiano zafferano - Laridi', 1240, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gavina - Laridi', 1250, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Mignattino alibianche  - Laridi', 1260, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Mignattino bigio - Laridi', 1270, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Mignattino comune - Laridi', 1280, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Mugnaiaccio - Laridi', 1290, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Rondine di mare zampenere - Laridi', 1300, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Sterna comune - Laridi', 1310, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gruccione - Meropidi', 1320, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Ballerina bianca - Motacillidi', 1330, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Ballerina gialla - Motacillidi', 1340, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Calandro - Motacillidi', 1350, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cutrettola - Motacillidi', 1360, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pispola - Motacillidi', 1370, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Prispolone - Motacillidi', 1380, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Spioncello - Motacillidi', 1390, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Balia dal collare - Muscicapidi', 1400, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Beccafico - Muscicapidi', 1410, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Beccamoschino - Muscicapidi', 1420, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Canapino maggiore - Muscicapidi', 1430, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cannareccione - Muscicapidi', 1440, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cesena - Muscicapidi', 1450, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Codirosso comune - Muscicapidi', 1460, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Codirosso spazzacamino - Muscicapidi', 1470, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Codirossone - Muscicapidi', 1480, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Culbianco - Muscicapidi', 1490, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Fiorrancino - Muscicapidi', 1500, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Forapaglie castagnolo - Muscicapidi', 1510, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Lui bianco - Muscicapidi', 1520, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Lui grosso - Muscicapidi', 1530, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Lui piccolo  - Muscicapidi', 1540, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Lui verde - Muscicapidi', 1550, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Merlo dal collare - Muscicapidi', 1560, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Monachella - Muscicapidi', 1570, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Occhiocotto - Muscicapidi', 1580, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Passero solitario - Muscicapidi', 1590, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pettazzurro - Muscicapidi', 1600, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pettirosso - Muscicapidi', 1610, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Regolo - Muscicapidi', 1620, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Saltimpalo - Muscicapidi', 1630, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Sterpazzola - Muscicapidi', 1640, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Sterpazzolina - Muscicapidi', 1650, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Stiaccino - Muscicapidi', 1660, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Tordela - Muscicapidi', 1670, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Tordo bottaccio - Muscicapidi', 1680, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Tordo sassello - Muscicapidi', 1690, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Usignolo - Muscicapidi', 1700, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Usignolo di fiume - Muscicapidi', 1710, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Rigogolo - Oriolidi', 1720, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cincia bigia - Paridi', 1730, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cincia mora - Paridi', 1740, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cinciallegra - Paridi', 1750, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cinciarella - Paridi', 1760, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Codibugniolo - Paridi', 1770, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pendolino - Paridi', 1780, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Picchio cenerino - Picidi', 1790, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Picchio nero - Picidi', 1800, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Picchio rosso maggiore - Picidi', 1810, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Picchio rosso minore - Picidi', 1820, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Picchio tridattilo - Picidi', 1830, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Picchio verde - Picidi', 1840, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Torcicollo comune - Picidi', 1850, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Passero comune - Ploceidi', 1860, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Passero mattugio - Ploceidi', 1870, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Passera scopaiola - Prunellidi', 1880, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Sordone - Prunellidi', 1890, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Folaga europea - Rallidi', 1900, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Porciglione - Rallidi', 1910, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Re di qualglie - Rallidi', 1920, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Schiribilla - Rallidi', 1930, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Schiribilla grigiata - Rallidi', 1940, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Voltolino - Rallidi', 1950, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Cavaliere d''Italia - Recurvirostridi', 1960, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Beccaccia - Scolapacidi', 1970, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Beccaccino - Scolapacidi', 1980, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Chiurlo maggiore - Scolapacidi', 1990, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Chiurlo piccolo - Scolapacidi', 2000, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Combattente - Scolapacidi', 2010, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Croccolone - Scolapacidi', 2020, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Frullino - Scolapacidi', 2030, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gambecchio - Scolapacidi', 2040, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gambecchio nano - Scolapacidi', 2050, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pantana - Scolapacidi', 2060, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pettegola - Scolapacidi', 2070, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piovanello - Scolapacidi', 2080, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piovanello maggiore - Scolapacidi', 2090, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piovanello pancianera - Scolapacidi', 2100, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piovanello tridattilo - Scolapacidi', 2110, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piro-piro boschereccio - Scolapacidi', 2120, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piro-piro culbianco - Scolapacidi', 2130, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Piro-piro piccolo - Scolapacidi', 2140, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Totano moro - Scolapacidi', 2160, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Voltapietre - Scolopacidi', 2170, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pittima minore - Scolopacidi', 2180, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pittima reale - Scolopacidi', 2190, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Stercorario maggiore - Stercoraridi', 2200, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gufo di palude - Stringidi', 2210, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gufo reale - Stringidi', 2220, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Gallo cedrone - Tetraonidi', 2230, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Pernice bianca nordica - Tetraonidi', 2240, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Spatola - Treschiornitidi', 2250, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Scricciolo comune - Trogloditidi', 2260, true, true, false, false );
insert into lookup_specie_sinantropi( description, level, enabled, uccello, mammifero, rettile_anfibio ) values( 'Upupa  - Upupidi', 2270, true, true, false, false );

update lookup_specie_sinantropi set description = 'Falco pellegrino - Falconidi', level =  783 where description ilike '%Accipitriformi - Falco pelegrino%';
update lookup_specie_sinantropi set description = 'Falco pecchialiolo - Accipitridi', level =  73  where description ilike '%Accipitriformi - Falco pecchialiolo%' ;
update lookup_specie_sinantropi set description = 'Falco pescatore - Accipitridi', level =  75 where description ilike '%Accipitriformi - Falco pescatore%' ;
update lookup_specie_sinantropi set description = 'Poiana comune - Accipitridi', level =  105 where description ilike '%Accipitriformi - Poiana%' ;
update lookup_specie_sinantropi set description = 'Gheppio comune - Falconidi', level =  786 where description ilike '%Accipitriformi - Gheppio%' ;
update lookup_specie_sinantropi set description = 'Sparviere - Accipitridi', level =  85  where description ilike '%Accipitriformi - Sparviere%' ;
update lookup_specie_sinantropi set description = 'Nibbio bruno - Accipitridi', level =  77  where description ilike '%Accipitriformi - Nibbio bruno%';
update lookup_specie_sinantropi set description = 'Germano reale - Anatidi', level =  215 where description ilike '%Anseriformi -  Germano Reale%';
update lookup_specie_sinantropi set description = 'Airone cenerino - Ardeidi', level =  426  where description ilike '%Ciconiformi - Airone cenerino%';
update lookup_specie_sinantropi set description = 'Airone bianco comune - Ardeidi', level =  423  where description ilike '%Ciconiformi - Airone bianco comune%';
update lookup_specie_sinantropi set description = 'Colombo domestico - Columbidi' , level =  625 where description ilike '%Colombiformi - Colombo domestico%';
update lookup_specie_sinantropi set description = 'Tortora comune - Columbidi', level =  635  where description ilike '%Colombiformi -  Tortora%';
update lookup_specie_sinantropi set description = 'Gallinella d''acqua - Rallidi' where description ilike '%Gruiformi - Gallinella d’acqua%';
update lookup_specie_sinantropi set description = 'Storno comune - Sturnidi' where description ilike '%Passeriformi - Storno%';
update lookup_specie_sinantropi set description = 'Rondine comune - Irundinidi' where description ilike '%Passeriformi - Rondine comune%';
update lookup_specie_sinantropi set description = 'Rondone comune - Apodidi' where description ilike '%Passeriformi - Rondone comune%';
update lookup_specie_sinantropi set description = 'Merlo - Muscicapidi' where description ilike '%Passeriformi - Merlo%';
update lookup_specie_sinantropi set description = 'Gazza - Corvidi' where description ilike '%Passeriformi - Gazza%';
update lookup_specie_sinantropi set description = 'Fringuello - Fringillidi' where description ilike '%Passeriformi - Fringuello%';
update lookup_specie_sinantropi set description = 'Corvo - Corvidi' where description ilike '%Passeriformi - Corvo%';
update lookup_specie_sinantropi set description = 'Cardellino - Fringillidi' where description ilike '%Passeriformi - Cardellino%';
update lookup_specie_sinantropi set description = 'Capinera - Muscicapidi' where description ilike '%Passeriformi - Capinera%';
update lookup_specie_sinantropi set description = 'Allocco - Stringidi' where description ilike '%Strigiformi - Allocco%';
update lookup_specie_sinantropi set description = 'Assiolo - Stringidi' where description ilike '%Strigiformi - Assiolo%';
update lookup_specie_sinantropi set description = 'Barbagianni - Titonidi' where description ilike '%Strigiformi - Barbagianni%';
update lookup_specie_sinantropi set description = 'Civetta - Stringidi' where description ilike '%Strigiformi - Civetta%';
update lookup_specie_sinantropi set description = 'Gufo Comune - Stringidi' where description ilike '%Strigiformi - Gufo Comune%';
update lookup_specie_sinantropi set description = 'Gabbiano - Laridi' where description ilike '%Caradriformi - Gabbiano%';
delete from lookup_specie_sinantropi where description = 'Acquila minore - Accipitridi'

ALTER TABLE esame_istopatologico ADD COLUMN sala_settoria integer;
ALTER TABLE esame_istopatologico
  ADD CONSTRAINT fk55ce148a277386ed FOREIGN KEY (sala_settoria)
      REFERENCES lookup_autopsia_sala_settoria (id) ;

ALTER TABLE lookup_autopsia_sala_settoria ADD COLUMN esame_riferimento character varying;

update lookup_autopsia_sala_settoria set esame_riferimento = 'Necroscopico';

INSERT INTO lookup_autopsia_sala_settoria(
            id, description, enabled, level, esame_riferimento)
    VALUES (4, 'IZSM', true, 40, 'Istopatologico');
INSERT INTO lookup_autopsia_sala_settoria(
            id, description, enabled, level, esame_riferimento)
    VALUES (5, 'Unina', true, 50, 'Istopatologico');
    
ALTER TABLE lookup_autopsia_sala_settoria ADD COLUMN esterna boolean;
update lookup_autopsia_sala_settoria set esterna = false;

ALTER TABLE trasferimento ADD COLUMN automatico_necroscopia boolean;
ALTER TABLE trasferimento ALTER COLUMN automatico_necroscopia SET DEFAULT false;


ALTER TABLE lookup_operazioni_accettazione ADD COLUMN id_bdr integer;
update lookup_operazioni_accettazione set id_bdr = 1 where id = 1;
update lookup_operazioni_accettazione set id_bdr = 9 where id = 3;
update lookup_operazioni_accettazione set id_bdr = 16 where id = 4;
update lookup_operazioni_accettazione set id_bdr = 2 where id = 9;
update lookup_operazioni_accettazione set id_bdr = 24 where id = 51;
update lookup_operazioni_accettazione set id_bdr = 4 where id = 50;
update lookup_operazioni_accettazione set id_bdr = 6 where id = 7;
update lookup_operazioni_accettazione set id_bdr = 41 where id = 52;
update lookup_operazioni_accettazione set id_bdr = 12 where id = 6;
update lookup_operazioni_accettazione set id_bdr = 11 where id = 5;

insert into lookup_operazioni_accettazione( description, inbdr, canina, felina, sinantropi, level, enabled, 
											approfondimenti, approfondimento_diagnostico_medicina, richiesta_prelievi_malattie_infettive, 
											alta_specialita_chirurgica, diagnostica_strumentale, id_bdr  ) 
									values( 'Reimmissione', false, false, false, false, 10, true, 
											false, false, false, 
											false, false, 23 );
											
											
ALTER TABLE lookup_comuni ADD COLUMN codice_istat character varying;
update lookup_comuni set codice_istat = '65001' where id = 1;
update lookup_comuni set codice_istat = '63001' where id = 2;
update lookup_comuni set codice_istat = '63002' where id = 3;
update lookup_comuni set codice_istat = '63003' where id = 4;
update lookup_comuni set codice_istat = '65002' where id = 5;
update lookup_comuni set codice_istat = '64001' where id = 6;
update lookup_comuni set codice_istat = '61001' where id = 7;
update lookup_comuni set codice_istat = '62001' where id = 8;
update lookup_comuni set codice_istat = '65003' where id = 9;
update lookup_comuni set codice_istat = '' where id = 10;
update lookup_comuni set codice_istat = '65004' where id = 11;
update lookup_comuni set codice_istat = '61002' where id = 12;
update lookup_comuni set codice_istat = '64002' where id = 13;
update lookup_comuni set codice_istat = '65005' where id = 14;
update lookup_comuni set codice_istat = '61003' where id = 15;
update lookup_comuni set codice_istat = '65006' where id = 16;
update lookup_comuni set codice_istat = '62002' where id = 17;
update lookup_comuni set codice_istat = '63004' where id = 18;
update lookup_comuni set codice_istat = '64003' where id = 19;
update lookup_comuni set codice_istat = '65007' where id = 20;
update lookup_comuni set codice_istat = '62003' where id = 21;
update lookup_comuni set codice_istat = '62004' where id = 22;
update lookup_comuni set codice_istat = '65008' where id = 23;
update lookup_comuni set codice_istat = '64004' where id = 24;
update lookup_comuni set codice_istat = '64005' where id = 25;
update lookup_comuni set codice_istat = '61004' where id = 26;
update lookup_comuni set codice_istat = '' where id = 27;
update lookup_comuni set codice_istat = '62005' where id = 28;
update lookup_comuni set codice_istat = '62006' where id = 29;
update lookup_comuni set codice_istat = '63005' where id = 30;
update lookup_comuni set codice_istat = '65009' where id = 31;
update lookup_comuni set codice_istat = '65010' where id = 32;
update lookup_comuni set codice_istat = '' where id = 33;
update lookup_comuni set codice_istat = '65011' where id = 34;
update lookup_comuni set codice_istat = '64006' where id = 35;
update lookup_comuni set codice_istat = '65012' where id = 36;
update lookup_comuni set codice_istat = '64007' where id = 37;
update lookup_comuni set codice_istat = '64008' where id = 38;
update lookup_comuni set codice_istat = '61005' where id = 39;
update lookup_comuni set codice_istat = '63006' where id = 40;
update lookup_comuni set codice_istat = '' where id = 41;
update lookup_comuni set codice_istat = '64009' where id = 42;
update lookup_comuni set codice_istat = '61006' where id = 43;
update lookup_comuni set codice_istat = '64010' where id = 44;
update lookup_comuni set codice_istat = '63007' where id = 45;
update lookup_comuni set codice_istat = '65013' where id = 46;
update lookup_comuni set codice_istat = '' where id = 47;
update lookup_comuni set codice_istat = '62007' where id = 48;
update lookup_comuni set codice_istat = '65014' where id = 49;
update lookup_comuni set codice_istat = '65158' where id = 50;
update lookup_comuni set codice_istat = '' where id = 51;
update lookup_comuni set codice_istat = '61007' where id = 52;
update lookup_comuni set codice_istat = '65015' where id = 53;
update lookup_comuni set codice_istat = '62008' where id = 54;
update lookup_comuni set codice_istat = '64011' where id = 55;
update lookup_comuni set codice_istat = '62009' where id = 56;
update lookup_comuni set codice_istat = '64012' where id = 57;
update lookup_comuni set codice_istat = '63008' where id = 58;
update lookup_comuni set codice_istat = '63009' where id = 59;
update lookup_comuni set codice_istat = '65016' where id = 60;
update lookup_comuni set codice_istat = '63010' where id = 61;
update lookup_comuni set codice_istat = '62010' where id = 62;
update lookup_comuni set codice_istat = '65017' where id = 63;
update lookup_comuni set codice_istat = '65018' where id = 64;
update lookup_comuni set codice_istat = '62011' where id = 65;
update lookup_comuni set codice_istat = '65019' where id = 66;
update lookup_comuni set codice_istat = '61008' where id = 67;
update lookup_comuni set codice_istat = '61009' where id = 68;
update lookup_comuni set codice_istat = '64013' where id = 69;
update lookup_comuni set codice_istat = '63011' where id = 70;
update lookup_comuni set codice_istat = '64014' where id = 71;
update lookup_comuni set codice_istat = '64015' where id = 72;
update lookup_comuni set codice_istat = '65020' where id = 73;
update lookup_comuni set codice_istat = '62012' where id = 74;
update lookup_comuni set codice_istat = '61010' where id = 75;
update lookup_comuni set codice_istat = '63012' where id = 76;
update lookup_comuni set codice_istat = '65021' where id = 77;
update lookup_comuni set codice_istat = '61011' where id = 78;
update lookup_comuni set codice_istat = '65022' where id = 79;
update lookup_comuni set codice_istat = '62013' where id = 80;
update lookup_comuni set codice_istat = '62014' where id = 81;
update lookup_comuni set codice_istat = '65023' where id = 82;
update lookup_comuni set codice_istat = '63013' where id = 83;
update lookup_comuni set codice_istat = '61012' where id = 84;
update lookup_comuni set codice_istat = '64016' where id = 85;
update lookup_comuni set codice_istat = '65024' where id = 86;
update lookup_comuni set codice_istat = '65025' where id = 87;
update lookup_comuni set codice_istat = '' where id = 88;
update lookup_comuni set codice_istat = '61013' where id = 89;
update lookup_comuni set codice_istat = '64017' where id = 90;
update lookup_comuni set codice_istat = '63014' where id = 91;
update lookup_comuni set codice_istat = '61014' where id = 92;
update lookup_comuni set codice_istat = '64018' where id = 93;
update lookup_comuni set codice_istat = '61015' where id = 94;
update lookup_comuni set codice_istat = '63015' where id = 95;
update lookup_comuni set codice_istat = '63016' where id = 96;
update lookup_comuni set codice_istat = '64019' where id = 97;
update lookup_comuni set codice_istat = '61016' where id = 98;
update lookup_comuni set codice_istat = '61017' where id = 99;
update lookup_comuni set codice_istat = '61018' where id = 100;
update lookup_comuni set codice_istat = '61019' where id = 101;
update lookup_comuni set codice_istat = '65028' where id = 102;
update lookup_comuni set codice_istat = '' where id = 103;
update lookup_comuni set codice_istat = '64020' where id = 104;
update lookup_comuni set codice_istat = '65026' where id = 105;
update lookup_comuni set codice_istat = '62015' where id = 106;
update lookup_comuni set codice_istat = '65027' where id = 107;
update lookup_comuni set codice_istat = '63017' where id = 108;
update lookup_comuni set codice_istat = '61020' where id = 109;
update lookup_comuni set codice_istat = '63018' where id = 110;
update lookup_comuni set codice_istat = '63019' where id = 111;
update lookup_comuni set codice_istat = '63020' where id = 112;
update lookup_comuni set codice_istat = '61103' where id = 113;
update lookup_comuni set codice_istat = '61021' where id = 114;
update lookup_comuni set codice_istat = '63021' where id = 115;
update lookup_comuni set codice_istat = '65029' where id = 116;
update lookup_comuni set codice_istat = '61022' where id = 117;
update lookup_comuni set codice_istat = '63022' where id = 118;
update lookup_comuni set codice_istat = '63023' where id = 119;
update lookup_comuni set codice_istat = '64021' where id = 120;
update lookup_comuni set codice_istat = '64022' where id = 121;
update lookup_comuni set codice_istat = '61023' where id = 122;
update lookup_comuni set codice_istat = '61024' where id = 123;
update lookup_comuni set codice_istat = '61026' where id = 124;
update lookup_comuni set codice_istat = '65034' where id = 125;
update lookup_comuni set codice_istat = '65035' where id = 126;
update lookup_comuni set codice_istat = '61027' where id = 127;
update lookup_comuni set codice_istat = '65030' where id = 128;
update lookup_comuni set codice_istat = '64023' where id = 129;
update lookup_comuni set codice_istat = '62016' where id = 130;
update lookup_comuni set codice_istat = '65031' where id = 131;
update lookup_comuni set codice_istat = '63024' where id = 132;
update lookup_comuni set codice_istat = '61025' where id = 133;
update lookup_comuni set codice_istat = '63025' where id = 134;
update lookup_comuni set codice_istat = '65032' where id = 135;
update lookup_comuni set codice_istat = '65033' where id = 136;
update lookup_comuni set codice_istat = '62017' where id = 137;
update lookup_comuni set codice_istat = '62018' where id = 138;
update lookup_comuni set codice_istat = '62019' where id = 139;
update lookup_comuni set codice_istat = '62020' where id = 140;
update lookup_comuni set codice_istat = '64024' where id = 141;
update lookup_comuni set codice_istat = '65036' where id = 142;
update lookup_comuni set codice_istat = '62021' where id = 143;
update lookup_comuni set codice_istat = '65037' where id = 144;
update lookup_comuni set codice_istat = '65038' where id = 145;
update lookup_comuni set codice_istat = '61102' where id = 146;
update lookup_comuni set codice_istat = '65039' where id = 147;
update lookup_comuni set codice_istat = '62022' where id = 148;
update lookup_comuni set codice_istat = '65040' where id = 149;
update lookup_comuni set codice_istat = '63026' where id = 150;
update lookup_comuni set codice_istat = '62023' where id = 151;
update lookup_comuni set codice_istat = '64025' where id = 152;
update lookup_comuni set codice_istat = '61028' where id = 153;
update lookup_comuni set codice_istat = '61029' where id = 154;
update lookup_comuni set codice_istat = '64026' where id = 155;
update lookup_comuni set codice_istat = '65041' where id = 156;
update lookup_comuni set codice_istat = '' where id = 157;
update lookup_comuni set codice_istat = '64027' where id = 158;
update lookup_comuni set codice_istat = '' where id = 159;
update lookup_comuni set codice_istat = '64028' where id = 160;
update lookup_comuni set codice_istat = '63027' where id = 161;
update lookup_comuni set codice_istat = '65042' where id = 162;
update lookup_comuni set codice_istat = '63028' where id = 163;
update lookup_comuni set codice_istat = '61030' where id = 164;
update lookup_comuni set codice_istat = '62024' where id = 165;
update lookup_comuni set codice_istat = '' where id = 166;
update lookup_comuni set codice_istat = '62025' where id = 167;
update lookup_comuni set codice_istat = '65043' where id = 168;
update lookup_comuni set codice_istat = '63029' where id = 169;
update lookup_comuni set codice_istat = '65044' where id = 170;
update lookup_comuni set codice_istat = '61031' where id = 171;
update lookup_comuni set codice_istat = '64029' where id = 172;
update lookup_comuni set codice_istat = '65045' where id = 173;
update lookup_comuni set codice_istat = '65046' where id = 174;
update lookup_comuni set codice_istat = '64030' where id = 175;
update lookup_comuni set codice_istat = '65047' where id = 176;
update lookup_comuni set codice_istat = '65048' where id = 177;
update lookup_comuni set codice_istat = '63030' where id = 178;
update lookup_comuni set codice_istat = '65049' where id = 179;
update lookup_comuni set codice_istat = '61032' where id = 180;
update lookup_comuni set codice_istat = '62026' where id = 181;
update lookup_comuni set codice_istat = '64031' where id = 182;
update lookup_comuni set codice_istat = '61033' where id = 183;
update lookup_comuni set codice_istat = '62027' where id = 184;
update lookup_comuni set codice_istat = '62028' where id = 185;
update lookup_comuni set codice_istat = '65050' where id = 186;
update lookup_comuni set codice_istat = '63064' where id = 187;
update lookup_comuni set codice_istat = '62029' where id = 188;
update lookup_comuni set codice_istat = '61101' where id = 189;
update lookup_comuni set codice_istat = '65051' where id = 190;
update lookup_comuni set codice_istat = '' where id = 191;
update lookup_comuni set codice_istat = '65052' where id = 192;
update lookup_comuni set codice_istat = '64032' where id = 193;
update lookup_comuni set codice_istat = '62030' where id = 194;
update lookup_comuni set codice_istat = '' where id = 195;
update lookup_comuni set codice_istat = '62031' where id = 196;
update lookup_comuni set codice_istat = '64033' where id = 197;
update lookup_comuni set codice_istat = '61034' where id = 198;
update lookup_comuni set codice_istat = '62032' where id = 199;
update lookup_comuni set codice_istat = '64034' where id = 200;
update lookup_comuni set codice_istat = '63031' where id = 201;
update lookup_comuni set codice_istat = '61035' where id = 202;
update lookup_comuni set codice_istat = '62033' where id = 203;
update lookup_comuni set codice_istat = '62034' where id = 204;
update lookup_comuni set codice_istat = '61036' where id = 205;
update lookup_comuni set codice_istat = '62035' where id = 206;
update lookup_comuni set codice_istat = '63032' where id = 207;
update lookup_comuni set codice_istat = '63033' where id = 208;
update lookup_comuni set codice_istat = '64035' where id = 209;
update lookup_comuni set codice_istat = '61037' where id = 210;
update lookup_comuni set codice_istat = '65053' where id = 211;
update lookup_comuni set codice_istat = '65054' where id = 212;
update lookup_comuni set codice_istat = '' where id = 213;
update lookup_comuni set codice_istat = '61038' where id = 214;
update lookup_comuni set codice_istat = '61039' where id = 215;
update lookup_comuni set codice_istat = '64036' where id = 216;
update lookup_comuni set codice_istat = '61040' where id = 217;
update lookup_comuni set codice_istat = '65055' where id = 218;
update lookup_comuni set codice_istat = '65056' where id = 219;
update lookup_comuni set codice_istat = '62036' where id = 220;
update lookup_comuni set codice_istat = '65057' where id = 221;
update lookup_comuni set codice_istat = '61041' where id = 222;
update lookup_comuni set codice_istat = '63034' where id = 223;
update lookup_comuni set codice_istat = '65058' where id = 224;
update lookup_comuni set codice_istat = '63035' where id = 225;
update lookup_comuni set codice_istat = '61042' where id = 226;
update lookup_comuni set codice_istat = '64037' where id = 227;
update lookup_comuni set codice_istat = '61043' where id = 228;
update lookup_comuni set codice_istat = '64038' where id = 229;
update lookup_comuni set codice_istat = '64039' where id = 230;
update lookup_comuni set codice_istat = '63036' where id = 231;
update lookup_comuni set codice_istat = '64040' where id = 232;
update lookup_comuni set codice_istat = '62037' where id = 233;
update lookup_comuni set codice_istat = '63037' where id = 234;
update lookup_comuni set codice_istat = '65059' where id = 235;
update lookup_comuni set codice_istat = '63038' where id = 236;
update lookup_comuni set codice_istat = '64041' where id = 237;
update lookup_comuni set codice_istat = '64042' where id = 238;
update lookup_comuni set codice_istat = '65060' where id = 239;
update lookup_comuni set codice_istat = '65061' where id = 240;
update lookup_comuni set codice_istat = '65062' where id = 241;
update lookup_comuni set codice_istat = '64043' where id = 242;
update lookup_comuni set codice_istat = '65063' where id = 243;
update lookup_comuni set codice_istat = '61044' where id = 244;
update lookup_comuni set codice_istat = '63039' where id = 245;
update lookup_comuni set codice_istat = '61045' where id = 246;
update lookup_comuni set codice_istat = '' where id = 247;
update lookup_comuni set codice_istat = '' where id = 248;
update lookup_comuni set codice_istat = '62038' where id = 249;
update lookup_comuni set codice_istat = '64044' where id = 250;
update lookup_comuni set codice_istat = '63040' where id = 251;
update lookup_comuni set codice_istat = '64045' where id = 252;
update lookup_comuni set codice_istat = '61046' where id = 253;
update lookup_comuni set codice_istat = '' where id = 254;
update lookup_comuni set codice_istat = '65064' where id = 255;
update lookup_comuni set codice_istat = '' where id = 256;
update lookup_comuni set codice_istat = '61047' where id = 257;
update lookup_comuni set codice_istat = '61048' where id = 258;
update lookup_comuni set codice_istat = '65065' where id = 259;
update lookup_comuni set codice_istat = '65066' where id = 260;
update lookup_comuni set codice_istat = '64046' where id = 261;
update lookup_comuni set codice_istat = '63041' where id = 262;
update lookup_comuni set codice_istat = '61049' where id = 263;
update lookup_comuni set codice_istat = '63042' where id = 264;
update lookup_comuni set codice_istat = '63043' where id = 265;
update lookup_comuni set codice_istat = '61050' where id = 266;
update lookup_comuni set codice_istat = '64047' where id = 267;
update lookup_comuni set codice_istat = '63092' where id = 268;
update lookup_comuni set codice_istat = '63044' where id = 269;
update lookup_comuni set codice_istat = '63045' where id = 270;
update lookup_comuni set codice_istat = '64048' where id = 271;
update lookup_comuni set codice_istat = '62039' where id = 272;
update lookup_comuni set codice_istat = '65067' where id = 273;
update lookup_comuni set codice_istat = '64049' where id = 274;
update lookup_comuni set codice_istat = '63046' where id = 275;
update lookup_comuni set codice_istat = '' where id = 276;
update lookup_comuni set codice_istat = '61051' where id = 277;
update lookup_comuni set codice_istat = '65068' where id = 278;
update lookup_comuni set codice_istat = '64050' where id = 279;
update lookup_comuni set codice_istat = '62040' where id = 280;
update lookup_comuni set codice_istat = '65069' where id = 281;
update lookup_comuni set codice_istat = '62041' where id = 282;
update lookup_comuni set codice_istat = '61052' where id = 283;
update lookup_comuni set codice_istat = '64051' where id = 284;
update lookup_comuni set codice_istat = '65070' where id = 285;
update lookup_comuni set codice_istat = '63047' where id = 286;
update lookup_comuni set codice_istat = '65075' where id = 287;
update lookup_comuni set codice_istat = '64052' where id = 288;
update lookup_comuni set codice_istat = '65071' where id = 289;
update lookup_comuni set codice_istat = '65072' where id = 290;
update lookup_comuni set codice_istat = '65073' where id = 291;
update lookup_comuni set codice_istat = '64053' where id = 292;
update lookup_comuni set codice_istat = '62042' where id = 293;
update lookup_comuni set codice_istat = '65074' where id = 294;
update lookup_comuni set codice_istat = '64054' where id = 295;
update lookup_comuni set codice_istat = '64055' where id = 296;
update lookup_comuni set codice_istat = '64056' where id = 297;
update lookup_comuni set codice_istat = '64057' where id = 298;
update lookup_comuni set codice_istat = '64058' where id = 299;
update lookup_comuni set codice_istat = '64059' where id = 300;
update lookup_comuni set codice_istat = '65076' where id = 301;
update lookup_comuni set codice_istat = '62043' where id = 302;
update lookup_comuni set codice_istat = '64060' where id = 303;
update lookup_comuni set codice_istat = '64061' where id = 304;
update lookup_comuni set codice_istat = '64062' where id = 305;
update lookup_comuni set codice_istat = '' where id = 306;
update lookup_comuni set codice_istat = '62044' where id = 307;
update lookup_comuni set codice_istat = '65077' where id = 308;
update lookup_comuni set codice_istat = '64063' where id = 309;
update lookup_comuni set codice_istat = '64064' where id = 310;
update lookup_comuni set codice_istat = '64065' where id = 311;
update lookup_comuni set codice_istat = '63048' where id = 312;
update lookup_comuni set codice_istat = '63049' where id = 313;
update lookup_comuni set codice_istat = '65078' where id = 314;
update lookup_comuni set codice_istat = '65079' where id = 315;
update lookup_comuni set codice_istat = '63050' where id = 316;
update lookup_comuni set codice_istat = '65080' where id = 317;
update lookup_comuni set codice_istat = '64066' where id = 318;
update lookup_comuni set codice_istat = '65081' where id = 319;
update lookup_comuni set codice_istat = '65082' where id = 320;
update lookup_comuni set codice_istat = '65083' where id = 321;
update lookup_comuni set codice_istat = '65084' where id = 322;
update lookup_comuni set codice_istat = '65085' where id = 323;
update lookup_comuni set codice_istat = '61053' where id = 324;
update lookup_comuni set codice_istat = '64067' where id = 325;
update lookup_comuni set codice_istat = '65086' where id = 326;
update lookup_comuni set codice_istat = '63051' where id = 327;
update lookup_comuni set codice_istat = '65087' where id = 328;
update lookup_comuni set codice_istat = '62045' where id = 329;
update lookup_comuni set codice_istat = '65088' where id = 330;
update lookup_comuni set codice_istat = '64068' where id = 331;
update lookup_comuni set codice_istat = '62046' where id = 332;
update lookup_comuni set codice_istat = '63052' where id = 333;
update lookup_comuni set codice_istat = '65089' where id = 334;
update lookup_comuni set codice_istat = '62047' where id = 335;
update lookup_comuni set codice_istat = '62048' where id = 336;
update lookup_comuni set codice_istat = '61054' where id = 337;
update lookup_comuni set codice_istat = '64069' where id = 338;
update lookup_comuni set codice_istat = '' where id = 339;
update lookup_comuni set codice_istat = '61055' where id = 340;
update lookup_comuni set codice_istat = '64070' where id = 341;
update lookup_comuni set codice_istat = '62049' where id = 342;
update lookup_comuni set codice_istat = '65090' where id = 343;
update lookup_comuni set codice_istat = '65091' where id = 344;
update lookup_comuni set codice_istat = '65092' where id = 345;
update lookup_comuni set codice_istat = '' where id = 346;
update lookup_comuni set codice_istat = '65093' where id = 347;
update lookup_comuni set codice_istat = '62050' where id = 348;
update lookup_comuni set codice_istat = '65094' where id = 349;
update lookup_comuni set codice_istat = '64071' where id = 350;
update lookup_comuni set codice_istat = '65095' where id = 351;
update lookup_comuni set codice_istat = '' where id = 352;
update lookup_comuni set codice_istat = '61056' where id = 353;
update lookup_comuni set codice_istat = '63053' where id = 354;
update lookup_comuni set codice_istat = '' where id = 355;
update lookup_comuni set codice_istat = '61057' where id = 356;
update lookup_comuni set codice_istat = '64072' where id = 357;
update lookup_comuni set codice_istat = '61058' where id = 358;
update lookup_comuni set codice_istat = '62051' where id = 359;
update lookup_comuni set codice_istat = '64073' where id = 360;
update lookup_comuni set codice_istat = '61059' where id = 361;
update lookup_comuni set codice_istat = '62052' where id = 362;
update lookup_comuni set codice_istat = '61060' where id = 363;
update lookup_comuni set codice_istat = '63054' where id = 364;
update lookup_comuni set codice_istat = '' where id = 365;
update lookup_comuni set codice_istat = '65096' where id = 366;
update lookup_comuni set codice_istat = '63055' where id = 367;
update lookup_comuni set codice_istat = '65097' where id = 368;
update lookup_comuni set codice_istat = '63056' where id = 369;
update lookup_comuni set codice_istat = '65098' where id = 370;
update lookup_comuni set codice_istat = '63057' where id = 371;
update lookup_comuni set codice_istat = '63058' where id = 372;
update lookup_comuni set codice_istat = '62053' where id = 373;
update lookup_comuni set codice_istat = '65099' where id = 374;
update lookup_comuni set codice_istat = '62054' where id = 375;
update lookup_comuni set codice_istat = '61061' where id = 376;
update lookup_comuni set codice_istat = '' where id = 377;
update lookup_comuni set codice_istat = '63059' where id = 378;
update lookup_comuni set codice_istat = '61062' where id = 379;
update lookup_comuni set codice_istat = '65100' where id = 380;
update lookup_comuni set codice_istat = '65101' where id = 381;
update lookup_comuni set codice_istat = '63060' where id = 382;
update lookup_comuni set codice_istat = '65102' where id = 383;
update lookup_comuni set codice_istat = '64074' where id = 384;
update lookup_comuni set codice_istat = '61063' where id = 385;
update lookup_comuni set codice_istat = '61064' where id = 386;
update lookup_comuni set codice_istat = '64075' where id = 387;
update lookup_comuni set codice_istat = '61065' where id = 388;
update lookup_comuni set codice_istat = '65103' where id = 389;
update lookup_comuni set codice_istat = '63061' where id = 390;
update lookup_comuni set codice_istat = '62055' where id = 391;
update lookup_comuni set codice_istat = '64076' where id = 392;
update lookup_comuni set codice_istat = '' where id = 393;
update lookup_comuni set codice_istat = '63062' where id = 394;
update lookup_comuni set codice_istat = '63063' where id = 395;
update lookup_comuni set codice_istat = '64077' where id = 396;
update lookup_comuni set codice_istat = '65104' where id = 397;
update lookup_comuni set codice_istat = '61066' where id = 398;
update lookup_comuni set codice_istat = '61067' where id = 399;
update lookup_comuni set codice_istat = '62056' where id = 400;
update lookup_comuni set codice_istat = '61068' where id = 401;
update lookup_comuni set codice_istat = '65105' where id = 402;
update lookup_comuni set codice_istat = '61069' where id = 403;
update lookup_comuni set codice_istat = '64079' where id = 404;
update lookup_comuni set codice_istat = '64078' where id = 405;
update lookup_comuni set codice_istat = '65106' where id = 406;
update lookup_comuni set codice_istat = '65107' where id = 407;
update lookup_comuni set codice_istat = '61070' where id = 408;
update lookup_comuni set codice_istat = '65108' where id = 409;
update lookup_comuni set codice_istat = '63065' where id = 410;
update lookup_comuni set codice_istat = '61071' where id = 411;
update lookup_comuni set codice_istat = '61072' where id = 412;
update lookup_comuni set codice_istat = '65109' where id = 413;
update lookup_comuni set codice_istat = '65110' where id = 414;
update lookup_comuni set codice_istat = '65111' where id = 415;
update lookup_comuni set codice_istat = '64080' where id = 416;
update lookup_comuni set codice_istat = '65112' where id = 417;
update lookup_comuni set codice_istat = '61073' where id = 418;
update lookup_comuni set codice_istat = '65113' where id = 419;
update lookup_comuni set codice_istat = '65114' where id = 420;
update lookup_comuni set codice_istat = '65115' where id = 421;
update lookup_comuni set codice_istat = '65116' where id = 422;
update lookup_comuni set codice_istat = '65117' where id = 423;
update lookup_comuni set codice_istat = '64081' where id = 424;
update lookup_comuni set codice_istat = '' where id = 425;
update lookup_comuni set codice_istat = '62057' where id = 426;
update lookup_comuni set codice_istat = '61074' where id = 427;
update lookup_comuni set codice_istat = '65118' where id = 428;
update lookup_comuni set codice_istat = '61075' where id = 429;
update lookup_comuni set codice_istat = '63066' where id = 430;
update lookup_comuni set codice_istat = '63067' where id = 431;
update lookup_comuni set codice_istat = '62058' where id = 432;
update lookup_comuni set codice_istat = '62059' where id = 433;
update lookup_comuni set codice_istat = '65119' where id = 434;
update lookup_comuni set codice_istat = '' where id = 435;
update lookup_comuni set codice_istat = '63068' where id = 436;
update lookup_comuni set codice_istat = '65120' where id = 437;
update lookup_comuni set codice_istat = '61076' where id = 438;
update lookup_comuni set codice_istat = '' where id = 439;
update lookup_comuni set codice_istat = '62060' where id = 440;
update lookup_comuni set codice_istat = '62061' where id = 441;
update lookup_comuni set codice_istat = '62062' where id = 442;
update lookup_comuni set codice_istat = '62063' where id = 443;
update lookup_comuni set codice_istat = '65121' where id = 444;
update lookup_comuni set codice_istat = '64082' where id = 445;
update lookup_comuni set codice_istat = '61077' where id = 446;
update lookup_comuni set codice_istat = '' where id = 447;
update lookup_comuni set codice_istat = '62064' where id = 448;
update lookup_comuni set codice_istat = '61104' where id = 449;
update lookup_comuni set codice_istat = '62065' where id = 450;
update lookup_comuni set codice_istat = '64083' where id = 451;
update lookup_comuni set codice_istat = '65122' where id = 452;
update lookup_comuni set codice_istat = '65123' where id = 453;
update lookup_comuni set codice_istat = '65124' where id = 454;
update lookup_comuni set codice_istat = '64084' where id = 455;
update lookup_comuni set codice_istat = '62066' where id = 456;
update lookup_comuni set codice_istat = '64085' where id = 457;
update lookup_comuni set codice_istat = '61078' where id = 458;
update lookup_comuni set codice_istat = '62067' where id = 459;
update lookup_comuni set codice_istat = '63069' where id = 460;
update lookup_comuni set codice_istat = '65125' where id = 461;
update lookup_comuni set codice_istat = '61079' where id = 462;
update lookup_comuni set codice_istat = '' where id = 463;
update lookup_comuni set codice_istat = '61080' where id = 464;
update lookup_comuni set codice_istat = '64086' where id = 465;
update lookup_comuni set codice_istat = '61081' where id = 466;
update lookup_comuni set codice_istat = '65126' where id = 467;
update lookup_comuni set codice_istat = '62068' where id = 468;
update lookup_comuni set codice_istat = '63070' where id = 469;
update lookup_comuni set codice_istat = '64087' where id = 470;
update lookup_comuni set codice_istat = '61085' where id = 471;
update lookup_comuni set codice_istat = '65132' where id = 472;
update lookup_comuni set codice_istat = '63075' where id = 473;
update lookup_comuni set codice_istat = '62069' where id = 474;
update lookup_comuni set codice_istat = '64088' where id = 475;
update lookup_comuni set codice_istat = '61082' where id = 476;
update lookup_comuni set codice_istat = '61083' where id = 477;
update lookup_comuni set codice_istat = '63090' where id = 478;
update lookup_comuni set codice_istat = '61084' where id = 479;
update lookup_comuni set codice_istat = '65127' where id = 480;
update lookup_comuni set codice_istat = '64093' where id = 481;
update lookup_comuni set codice_istat = '62070' where id = 482;
update lookup_comuni set codice_istat = '' where id = 483;
update lookup_comuni set codice_istat = '63071' where id = 484;
update lookup_comuni set codice_istat = '63072' where id = 485;
update lookup_comuni set codice_istat = '64089' where id = 486;
update lookup_comuni set codice_istat = '62071' where id = 487;
update lookup_comuni set codice_istat = '65128' where id = 488;
update lookup_comuni set codice_istat = '64091' where id = 489;
update lookup_comuni set codice_istat = '64090' where id = 490;
update lookup_comuni set codice_istat = '61086' where id = 491;
update lookup_comuni set codice_istat = '64092' where id = 492;
update lookup_comuni set codice_istat = '63073' where id = 493;
update lookup_comuni set codice_istat = '63074' where id = 494;
update lookup_comuni set codice_istat = '62078' where id = 495;
update lookup_comuni set codice_istat = '61087' where id = 496;
update lookup_comuni set codice_istat = '65129' where id = 497;
update lookup_comuni set codice_istat = '65130' where id = 498;
update lookup_comuni set codice_istat = '' where id = 499;
update lookup_comuni set codice_istat = '64095' where id = 500;
update lookup_comuni set codice_istat = '65131' where id = 501;
update lookup_comuni set codice_istat = '65133' where id = 502;
update lookup_comuni set codice_istat = '65134' where id = 503;
update lookup_comuni set codice_istat = '65135' where id = 504;
update lookup_comuni set codice_istat = '65136' where id = 505;
update lookup_comuni set codice_istat = '62072' where id = 506;
update lookup_comuni set codice_istat = '63076' where id = 507;
update lookup_comuni set codice_istat = '64096' where id = 508;
update lookup_comuni set codice_istat = '65137' where id = 509;
update lookup_comuni set codice_istat = '65138' where id = 510;
update lookup_comuni set codice_istat = '64097' where id = 511;
update lookup_comuni set codice_istat = '63077' where id = 512;
update lookup_comuni set codice_istat = '' where id = 513;
update lookup_comuni set codice_istat = '64098' where id = 514;
update lookup_comuni set codice_istat = '64099' where id = 515;
update lookup_comuni set codice_istat = '65139' where id = 516;
update lookup_comuni set codice_istat = '63078' where id = 517;
update lookup_comuni set codice_istat = '65140' where id = 518;
update lookup_comuni set codice_istat = '61088' where id = 519;
update lookup_comuni set codice_istat = '65141' where id = 520;
update lookup_comuni set codice_istat = '65142' where id = 521;
update lookup_comuni set codice_istat = '65143' where id = 522;
update lookup_comuni set codice_istat = '' where id = 523;
update lookup_comuni set codice_istat = '64100' where id = 524;
update lookup_comuni set codice_istat = '' where id = 525;
update lookup_comuni set codice_istat = '64101' where id = 526;
update lookup_comuni set codice_istat = '62073' where id = 527;
update lookup_comuni set codice_istat = '63079' where id = 528;
update lookup_comuni set codice_istat = '64102' where id = 529;
update lookup_comuni set codice_istat = '63080' where id = 530;
update lookup_comuni set codice_istat = '61089' where id = 531;
update lookup_comuni set codice_istat = '64103' where id = 532;
update lookup_comuni set codice_istat = '65144' where id = 533;
update lookup_comuni set codice_istat = '65145' where id = 534;
update lookup_comuni set codice_istat = '63081' where id = 535;
update lookup_comuni set codice_istat = '64104' where id = 536;
update lookup_comuni set codice_istat = '61090' where id = 537;
update lookup_comuni set codice_istat = '64105' where id = 538;
update lookup_comuni set codice_istat = '64106' where id = 539;
update lookup_comuni set codice_istat = '64107' where id = 540;
update lookup_comuni set codice_istat = '' where id = 541;
update lookup_comuni set codice_istat = '61091' where id = 542;
update lookup_comuni set codice_istat = '65146' where id = 543;
update lookup_comuni set codice_istat = '62074' where id = 544;
update lookup_comuni set codice_istat = '64108' where id = 545;
update lookup_comuni set codice_istat = '63082' where id = 546;
update lookup_comuni set codice_istat = '' where id = 547;
update lookup_comuni set codice_istat = '61092' where id = 548;
update lookup_comuni set codice_istat = '62075' where id = 549;
update lookup_comuni set codice_istat = '61093' where id = 550;
update lookup_comuni set codice_istat = '65147' where id = 551;
update lookup_comuni set codice_istat = '64109' where id = 552;
update lookup_comuni set codice_istat = '65148' where id = 553;
update lookup_comuni set codice_istat = '63083' where id = 554;
update lookup_comuni set codice_istat = '63084' where id = 555;
update lookup_comuni set codice_istat = '64110' where id = 556;
update lookup_comuni set codice_istat = '65149' where id = 557;
update lookup_comuni set codice_istat = '62076' where id = 558;
update lookup_comuni set codice_istat = '64111' where id = 559;
update lookup_comuni set codice_istat = '65150' where id = 560;
update lookup_comuni set codice_istat = '65151' where id = 561;
update lookup_comuni set codice_istat = '63091' where id = 562;
update lookup_comuni set codice_istat = '65152' where id = 563;
update lookup_comuni set codice_istat = '61094' where id = 564;
update lookup_comuni set codice_istat = '64112' where id = 565;
update lookup_comuni set codice_istat = '63085' where id = 566;
update lookup_comuni set codice_istat = '64113' where id = 567;
update lookup_comuni set codice_istat = '61095' where id = 568;
update lookup_comuni set codice_istat = '64114' where id = 569;
update lookup_comuni set codice_istat = '61096' where id = 570;
update lookup_comuni set codice_istat = '65153' where id = 571;
update lookup_comuni set codice_istat = '61097' where id = 572;
update lookup_comuni set codice_istat = '64115' where id = 573;
update lookup_comuni set codice_istat = '65154' where id = 574;
update lookup_comuni set codice_istat = '65155' where id = 575;
update lookup_comuni set codice_istat = '' where id = 576;
update lookup_comuni set codice_istat = '64116' where id = 577;
update lookup_comuni set codice_istat = '65156' where id = 578;
update lookup_comuni set codice_istat = '63086' where id = 579;
update lookup_comuni set codice_istat = '65157' where id = 580;
update lookup_comuni set codice_istat = '61098' where id = 581;
update lookup_comuni set codice_istat = '' where id = 582;
update lookup_comuni set codice_istat = '61099' where id = 583;
update lookup_comuni set codice_istat = '64117' where id = 584;
update lookup_comuni set codice_istat = '64118' where id = 585;
update lookup_comuni set codice_istat = '63087' where id = 586;
update lookup_comuni set codice_istat = '63088' where id = 587;
update lookup_comuni set codice_istat = '62077' where id = 588;
update lookup_comuni set codice_istat = '61100' where id = 589;
update lookup_comuni set codice_istat = '63089' where id = 590;
update lookup_comuni set codice_istat = '64119' where id = 591;
update lookup_comuni set codice_istat = '64120' where id = 592;

CREATE TABLE timbro_anagrafeanimali_storage
(
  id serial NOT NULL,
  specie integer,
  animale integer,
  microchip text,
  tipo text,
  version integer,
  nome_documento character varying(255),
  path character varying(255),
  data_creazione timestamp without time zone,
  utente_creazione character varying(255),
  utente_creazione_id integer,
  utente_creazione_ip character varying(255),
  path_server character varying(255),
  md5 character varying(255),
  letto integer NOT NULL DEFAULT 1,
  data_ultima_lettura timestamp without time zone,
  CONSTRAINT id_anagrafeanimali_documento PRIMARY KEY (id ),
  CONSTRAINT idanimale_fk FOREIGN KEY (animale)
      REFERENCES animale (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT idspecie_fk FOREIGN KEY (specie)
      REFERENCES lookup_specie (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE timbro_anagrafeanimali_storage
  OWNER TO postgres;
  
update lookup_operazioni_accettazione set obbligo_cc = false where id = 56

--L'adozione per utenti con asl diversa da quella dell'animale non si può fare, approvato da Veronica
update lookup_operazioni_accettazione set effettuabile_fuori_asl = false where description ilike '%adozion%'

update lookup_operazioni_accettazione set id_bdr = null where description ilike '%trasferimento%';





INSERT INTO lookup_operazioni_accettazione VALUES (54, false, true, true, true, 'Terapia in Degenza', false, true, true, false, 215, false, true, NULL, NULL, NULL, NULL, NULL, NULL, false, NULL);

CREATE TABLE timbro_anagrafeanimali_storage
(
  id serial NOT NULL,
  specie integer,
  animale integer,
  microchip text,
  tipo text,
  version integer,
  nome_documento character varying(255),
  path character varying(255),
  data_creazione timestamp without time zone,
  utente_creazione character varying(255),
  utente_creazione_id integer,
  utente_creazione_ip character varying(255),
  path_server character varying(255),
  md5 character varying(255),
  letto integer NOT NULL DEFAULT 1,
  data_ultima_lettura timestamp without time zone,
  CONSTRAINT id_anagrafeanimali_documento PRIMARY KEY (id ),
  CONSTRAINT idanimale_fk FOREIGN KEY (animale)
      REFERENCES animale (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT idspecie_fk FOREIGN KEY (specie)
      REFERENCES lookup_specie (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE timbro_anagrafeanimali_storage
  OWNER TO postgres;

  
update sinantropo set last_operation = 'RINVENIMENTO' where last_operation = 'CATTURA';
update sinantropo set last_operation = 'RILASCIO' where last_operation = 'REIMMISSIONE';

update lookup_autopsia_sala_settoria set enabled = true where esame_riferimento = 'Istopatologico';

update lookup_operazioni_accettazione set obbligo_cc = false where id = 56

-- Function: public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying)

-- DROP FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION public_functions.inseriscidecesso(microchip_to_get character varying, data timestamp without time zone, idcausadecesso integer, flagdatapresunta boolean, codiceistatcomunedecesso character varying, indirizzodecesso character varying, note character varying, user_username character varying)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idProssimoStato integer;
 cursore refcursor;
 cursore1 refcursor;
 cursore2 refcursor;
 idEvento integer;

 /*utente*/
 idUtente integer;
 idAslUtente integer;


 /*indirizzo*/
 idProvincia integer;
 idComune integer;

 
 BEGIN

 /*Dati animale*/
 open cursore for select id, id_asl_riferimento, id_specie, stato from animale where microchip = microchip_to_get; 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato;
EXIT WHEN NOT FOUND; 


/*dati utente*/
 
open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 

/*dati indirizzo decesso*/

open cursore2 for select id, cod_provincia from comuni1 where istat = codiceistatcomunedecesso;
LOOP
fetch cursore2 into idComune, idProvincia;
EXIT WHEN NOT FOUND; 


raise info 'datiutente % % ', idUtente, idAslUtente;

/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale, id_stato_originale, note)
    VALUES (idAnimale, microchip_to_get, now(), now(), idUtente, idUtente, 
            9, idAslUtente, idSpecie, idStato, note) RETURNING id_evento INTO idEvento;   

INSERT INTO evento_decesso(
            id_evento, data_decesso, flag_decesso_presunto, id_causa_decesso,   id_provincia_decesso,  id_comune_decesso, indirizzo_decesso)
    VALUES ( idEvento, data,  
            flagDataPresunta, idCausaDecesso, idProvincia, idComune, indirizzodecesso);





Select id_prossimo_stato into idProssimoStato from registrazioni_wk where id_stato = idStato and id_registrazione = 9;

Update animale set stato = idProssimoStato, flag_decesso=true where microchip = microchip_to_get;



 END LOOP;
  END LOOP;
  END LOOP;


return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying)
  OWNER TO postgres;

  
  
  
-- Function: public_functions.inseriscismarrimento(text, timestamp without time zone, text, character varying, boolean, double precision, character varying)

-- DROP FUNCTION public_functions.inseriscismarrimento(text, timestamp without time zone, text, character varying, boolean, double precision, character varying);

CREATE OR REPLACE FUNCTION public_functions.inseriscismarrimento(microchip_to_get text, data_smarrimento timestamp without time zone, luogo text, note character varying, flag boolean, importo double precision, user_username character varying)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idProssimoStato integer;
 cursore refcursor;
 cursore1 refcursor;
 idEvento integer;
 idAslUtente integer;
 idUtente integer;
 
 BEGIN
 open cursore for select id, id_asl_riferimento, id_specie, stato from animale where microchip = microchip_to_get; 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato;
EXIT WHEN NOT FOUND; 


open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 


/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale, id_stato_originale, note)
    VALUES (idAnimale, microchip_to_get, now(), now(), idUtente, idAslUtente, 
            11, idAsl, idSpecie, idStato, note) RETURNING id_evento INTO idEvento;   

INSERT INTO evento_smarrimento(
            id_evento, data_smarrimento, luogo_smarrimento, flag_importo_smarrimento, 
            importo_smarrimento)
    VALUES ( idEvento, data_smarrimento, luogo, flag, 
            importo);
Select id_prossimo_stato into idProssimoStato from registrazioni_wk where id_stato = idStato and id_registrazione = 11;

Update animale set stato = idProssimoStato where microchip = microchip_to_get;

end loop;
end loop;
   



return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inseriscismarrimento(text, timestamp without time zone, text, character varying, boolean, double precision, character varying)
  OWNER TO postgres;


  
  -- Function: public_functions.inserisciritrovamento(text, timestamp without time zone, character varying, character varying, character varying)

-- DROP FUNCTION public_functions.inserisciritrovamento(text, timestamp without time zone, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION public_functions.inserisciritrovamento(microchip_to_get text, data timestamp without time zone, luogo character varying, codiceistatcomune character varying, note character varying, user_username character varying)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idProssimoStato integer;
 cursore refcursor;
 cursore1 refcursor;
 cursore2 refcursor;
 idEvento integer;
 idUtente integer;
 idAslUtente integer;
 idComune integer;

 BEGIN

 
 open cursore for select id, id_asl_riferimento, id_specie, stato from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get); 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato;
EXIT WHEN NOT FOUND; 

open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 


open cursore2 for select id from comuni1 where istat = codiceistatcomune;
LOOP
fetch cursore2 into idComune;
EXIT WHEN NOT FOUND; 

/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale)
    VALUES (idAnimale, microchip_to_get, now(), now(), -1, -1, 
            12, idAsl, idSpecie) RETURNING id_evento INTO idEvento;   

INSERT INTO evento_ritrovamento(
            id_evento, data_ritrovamento, luogo_ritrovamento, comune_ritrovamento)
    VALUES ( idEvento, data, luogo, idComune);




Select id_prossimo_stato into idProssimoStato from registrazioni_wk where id_stato = idStato and id_registrazione = 12;

Update animale set stato = idProssimoStato where (microchip = microchip_to_get or tatuaggio = microchip_to_get);


end loop;  
end loop;
end loop;

return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inserisciritrovamento(text, timestamp without time zone, character varying, character varying, character varying)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.inserisciritrovamento(text, timestamp without time zone, character varying, character varying, character varying) TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.inserisciritrovamento(text, timestamp without time zone, character varying, character varying, character varying) TO public;


-- Function: public_functions.inserisciritrovamentosmarrnondenunciato(text, timestamp without time zone, character varying, character varying, character varying, character varying)

-- DROP FUNCTION public_functions.inserisciritrovamentosmarrnondenunciato(text, timestamp without time zone, character varying, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION public_functions.inserisciritrovamentosmarrnondenunciato(microchip_to_get text, data timestamp without time zone, luogo character varying, codiceistatcomune character varying, note character varying, user_username character varying)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idProssimoStato integer;
 cursore refcursor;
 cursore1 refcursor;
 cursore2 refcursor;
 idEvento integer;
 idUtente integer;
 idAslUtente integer;
 idComune integer;
 BEGIN

 
 open cursore for select id, id_asl_riferimento, id_specie, stato from animale where  (microchip = microchip_to_get or tatuaggio = microchip_to_get); 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato;
EXIT WHEN NOT FOUND; 

open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 

open cursore2 for select id from comuni1 where istat = codiceistatcomune;
LOOP
fetch cursore2 into idComune;
EXIT WHEN NOT FOUND; 

/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale)
    VALUES (idAnimale, microchip_to_get, now(), now(), -1, -1, 
            12, idAsl, idSpecie) RETURNING id_evento INTO idEvento;   

INSERT INTO evento_ritrovamento(
            id_evento, data_ritrovamento, luogo_ritrovamento, comune_ritrovamento)
    VALUES ( idEvento, data, luogo, idComune);




Select id_prossimo_stato into idProssimoStato from registrazioni_wk where id_stato = idStato and id_registrazione = 12;

Update animale set stato = idStato where (microchip = microchip_to_get or tatuaggio = microchip_to_get);


end loop;  
end loop;
end loop;

return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inserisciritrovamentosmarrnondenunciato(text, timestamp without time zone, character varying, character varying, character varying, character varying)
  OWNER TO postgres;
  
  ALTER TABLE attivita_bdr ADD COLUMN cc integer;
ALTER TABLE attivita_bdr
  ADD CONSTRAINT fk42f829f96c5b1498 FOREIGN KEY (cc)
      REFERENCES cartella_clinica(id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;

      
CREATE OR REPLACE FUNCTION public_functions.getultimaregistrazione(microchip_to_get character varying, id_tipo_reg integer)
  RETURNS integer AS
$BODY$
DECLARE  
 rec int;  
BEGIN 


select    e.id_evento as id
into rec
from animale a,
     evento e,
     lookup_tipologia_registrazione t_reg
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and
      a.id = e.id_animale and 
      t_reg.code = id_tipo_reg and 
      e.id_tipologia_evento = t_reg.code
order by e.entered desc
limit 1;


return rec;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getultimaregistrazione(character varying, integer)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getultimaregistrazione(character varying, integer) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getultimaregistrazione(character varying, integer) TO postgres;

-- Function: public_functions.get_id_tipo_ultima_registrazione(character varying)

-- DROP FUNCTION public_functions.get_id_tipo_ultima_registrazione(character varying);

CREATE OR REPLACE FUNCTION public_functions.get_id_tipo_ultima_registrazione(microchip_to_get character varying)
  RETURNS integer AS
$BODY$
DECLARE  
 rec int;  
BEGIN 


select    t_reg.code as id
into rec
from animale a,
     evento e,
     lookup_tipologia_registrazione t_reg
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and
      a.id = e.id_animale and 
      e.id_tipologia_evento = t_reg.code
order by e.entered desc
limit 1;


return rec;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.get_id_tipo_ultima_registrazione(character varying)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.get_id_tipo_ultima_registrazione(character varying) TO public;
GRANT EXECUTE ON FUNCTION public_functions.get_id_tipo_ultima_registrazione(character varying) TO postgres;




insert into lookup_operazioni_accettazione values(
57,FALSE,FALSE,FALSE,TRUE,'Prelievo DNA',FALSE,TRUE,TRUE,TRUE,220,TRUE,FALSE,FALSE,TRUE,'',FALSE,TRUE,TRUE,FALSE,50);


--Inserire come anomalie di ‘Mammelle’ e ‘Testicoli’ le stesse usate per ‘Denti’.
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 1 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 2 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 3 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 4 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 5 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 6 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 7 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 8 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 9 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 10 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 1 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 2 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 3 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 4 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 5 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 6 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 7 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 8 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 9 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 10 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 1 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 2 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 3 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 4 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 5 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 6 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 7 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 8 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 9 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 10 );

--Inserire ‘Pigmentazioni patologiche’ tra le anomalie degli organi con già un elenco associato.
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 135, 'Pigmentazioni patologiche', 1000, true );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 34, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 43, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 25, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 32, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 8, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 12, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 1, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 10, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 26, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 42, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 11, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 18, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 16, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 39, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 3, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 61, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 13, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 22, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 9, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 24, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 64, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 14, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 27, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 17, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 28, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 36, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 15, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 38, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 4, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 30, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 74, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 33, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 73, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 6, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 40, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 71, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 19, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 29, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 21, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 23, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 41, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 31, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 35, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 20, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 5, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 7, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 37, 135 );

ALTER TABLE lookup_operazioni_accettazione ADD COLUMN enabled_in_page boolean;
update lookup_operazioni_accettazione set enabled_in_page = true;
update lookup_operazioni_accettazione set enabled_in_page = false where id in (5,50);

ALTER TABLE accettazione ADD COLUMN richiedente_tipo_proprietario text;
ALTER TABLE accettazione ADD COLUMN proprietario_tipo text;





-- Type: public_functions.dati_proprietario_animale_to_vam

-- DROP TYPE public_functions.dati_proprietario_animale_to_vam;

CREATE TYPE public_functions.dati_proprietario_animale_to_vam AS
   (id integer,
    nome text,
    cognome text,
    codicefiscale text,
    documentoidentita text,
    citta character varying,
    provincia text,
    nazione character varying,
    via character varying,
    cap text,
    trasheddate timestamp without time zone,
    id_asl integer,
    numerotelefono character(50),
    tipo character varying);
ALTER TYPE public_functions.dati_proprietario_animale_to_vam
  OWNER TO postgres;
  
  
-- Function: public_functions.getdatiproprietarioanimale(text)

-- DROP FUNCTION public_functions.getdatiproprietarioanimale(text);

CREATE OR REPLACE FUNCTION public_functions.getdatiproprietarioanimale(microchip_to_get text)
  RETURNS public_functions.dati_proprietario_animale_to_vam AS
$BODY$
DECLARE  
 relazione_privato CONSTANT integer := 1; /* PROPRIETARIO DI TIPO PRIVATO*/
 relazione_canile  CONSTANT integer := 5; /* PROPRIETARIO DI TIPO CANILE*/
 relazione_sindaco CONSTANT integer := 3; /* PROPRIETARIO DI TIPO SINDACO*/
 relazione_colonia CONSTANT integer := 9; /* PROPRIETARIO DI TIPO COLONIA*/
 relazione_sindaco_fr CONSTANT integer := 7; /* PROPRIETARIO DI TIPO SINDACO FR*/
 relazione_operatore_commerciale CONSTANT integer := 6; /* PROPRIETARIO OPERATORE COMMERCIALE*/
 relazione_importatore CONSTANT integer := 4; /* PROPRIETARIO IMPORTATORE*/
 tipologia integer;
 rec public_functions.dati_proprietario_animale_to_vam;  
BEGIN 
/*Recupero la tipologia di linea produttiva */
Select id_linea_produttiva into tipologia from animale 
join opu_relazione_stabilimento_linee_produttive rel on(animale.id_proprietario = rel.id) where (animale.microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null; 


raise info 'dati % ', tipologia;
CASE tipologia

WHEN relazione_privato then

/* indirizzo dell'operatore, corrispondente all'indirizzo del sogg fisico*/
  Select r.id, sog.nome, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,   indirizzo.cap,  
o.trashed_date, s.id_asl,sog.telefono , 'Privato' as tipo into rec from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);


WHEN relazione_canile THEN
/* Sede operativa */
Select r.id, o.ragione_sociale, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,   indirizzo.cap, 
o.trashed_date, s.id_asl, sog.telefono , 'Canile' as tipo  into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);


WHEN relazione_sindaco THEN 
/* indirizzo dell'operatore, corrispondente all'indirizzo del sogg fisico*/
Select r.id, sog.nome, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,  indirizzo.cap,   
o.trashed_date, s.id_asl, sog.telefono , 'Sindaco' as tipo  into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_relazione_operatore_sede relindirizzo on (o.id = relindirizzo.id_operatore)
join opu_indirizzo indirizzo on (relindirizzo.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);

WHEN relazione_colonia THEN
/* Sede operativa  corrispondente a indirizzo colonia*/
Select r.id, info.nr_protocollo, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,  indirizzo.cap,  
o.trashed_date, s.id_asl,sog.telefono , 'Colonia' as tipo  into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
join opu_informazioni_colonia info on (r.id = info.id_relazione_stabilimento_linea_produttiva)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);



WHEN relazione_sindaco_fr THEN 
/* indirizzo dell'operatore, corrispondente all'indirizzo del sogg fisico*/
Select r.id, sog.nome, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,  indirizzo.cap, 
o.trashed_date, s.id_asl, sog.telefono  , 'Sindaco' as tipo into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_relazione_operatore_sede relindirizzo on (o.id = relindirizzo.id_operatore)
join opu_indirizzo indirizzo on (relindirizzo.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);



WHEN relazione_operatore_commerciale THEN
/* sede operativa*/
Select r.id, o.ragione_sociale, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via, indirizzo.cap,
o.trashed_date, s.id_asl, sog.telefono, 'Operatore Commerciale' as tipo into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);


WHEN relazione_importatore THEN
/* sede operativa*/
Select r.id, o.ragione_sociale, o.partita_iva as cognome, sog.codice_fiscale, r.telefono2 as documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,  indirizzo.cap,
o.trashed_date, s.id_asl,  r.telefono1 as telefono , 'Importatore' as tipo into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);



ELSE 

Select null into rec;



END CASE;
return rec;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getdatiproprietarioanimale(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getdatiproprietarioanimale(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getdatiproprietarioanimale(text) TO postgres;

ALTER TABLE lookup_autopsia_patologie_prevalenti ADD COLUMN definitiva boolean;
ALTER TABLE lookup_autopsia_patologie_prevalenti ALTER COLUMN definitiva SET DEFAULT false;

update lookup_autopsia_patologie_prevalenti set definitiva = false;
update lookup_autopsia_patologie_prevalenti set definitiva = true where (id>=1  and id <= 10) or id = 135;

ALTER TABLE autopsia ADD COLUMN patologia_definitiva integer;
ALTER TABLE autopsia
  ADD CONSTRAINT fk55ce148a42de61c9 FOREIGN KEY (patologia_definitiva)
      REFERENCES lookup_autopsia_patologie_prevalenti (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;
      
      
      
ALTER TABLE accettazione ADD COLUMN adozione_fuori_asl boolean;
ALTER TABLE lookup_operazioni_accettazione ADD COLUMN intra_fuori_asl boolean;



ALTER TABLE lookup_autopsia_organi ADD COLUMN mammiferi boolean;
ALTER TABLE lookup_autopsia_organi ADD COLUMN rettili boolean;
update lookup_autopsia_organi set mammiferi = cani, rettili = false

insert into lookup_autopsia_organi( description, level_sde, enabled, tessuto, enabled_sde, level, cani, gatti,uccelli,mammiferi,rettili ) values( 'Orecchio esterno', 237, true, true , true, 111, true, true,false,true,false);
update lookup_autopsia_organi set level = 114 where id = 53;
update lookup_autopsia_organi set level = 112 where id = 58;
update lookup_autopsia_organi set level = 113 where id = 59;
update lookup_autopsia_organi set level = 280 where id = 24;
update lookup_autopsia_organi set level = 270 where id = 25;
insert into lookup_autopsia_organi( description, level_sde, enabled, tessuto, enabled_sde, level, cani, gatti,uccelli,mammiferi,rettili ) values( 'Vagina', 430, true, false , true, 285, true, true,false,true,false);
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 1 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 2 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 3 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 4 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 5 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 6 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 7 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 8 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 9 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 10 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 135 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 1 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 2 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 3 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 4 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 5 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 6 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 7 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 8 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 9 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 10 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 135 );



insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled, definitiva ) values( 136, 'Modificazioni post-mortali', 1000, true, true );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 34, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 43, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 25, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 32, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 8, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 12, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 1, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 10, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 26, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 42, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 11, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 18, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 16, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 39, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 3, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 61, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 13, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 22, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 63, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 9, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 67, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 24, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 64, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 70, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 14, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 46, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 27, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 17, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 28, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 68, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 36, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 15, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 66, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 38, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 4, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 30, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 60, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 74, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 33, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 73, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 6, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 40, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 62, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 71, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 19, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 29, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 21, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 2, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 57, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 72, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 23, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 41, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 31, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 35, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 65, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 20, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 69, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 5, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 7, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 37, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 75, 136 );
insert into organi_patologieprevalenti( organo, patologia_prevalente ) values( 76, 136 );



select max(id) from capability

INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (404, 'Ambulatorio - Veterinario', NULL, 'GESTIONE_ISTOPATOLOGICO->ADD->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 404, 'w');

INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (405, 'Ambulatorio - Veterinario 3', NULL, 'GESTIONE_ISTOPATOLOGICO->ADD->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 405, 'w');

INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (406,  'Sinantropi', NULL, 'GESTIONE_ISTOPATOLOGICO->ADD->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 406, 'w');

INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (407, 'Referente Asl', NULL, 'GESTIONE_ISTOPATOLOGICO->ADD->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 407, 'w');


--da lanciare sul db di BDU
CREATE OR REPLACE FUNCTION public_functions.getdaticolonia(microchip_to_get text)
  RETURNS public_functions.dati_colonia_to_vam AS
$BODY$
DECLARE  
rec public_functions.dati_colonia_to_vam;  
BEGIN 

Select r.id_rel_stab_lp, r.ragione_sociale, r.ragione_sociale,r.codice_fiscale, sog.documento_identita, c.nome as comune, indirizzo.provincia,
indirizzo.nazione,  indirizzo.via,  indirizzo.cap, o.trashed_date, r.id_asl, sog.telefono,info.nr_protocollo, info.data_censimento_totale, 
info.nr_totale_gatti, info.data_registrazione_colonia, info.nominativo_veterinario into rec
from  opu_operatori_denormalizzati r 
join opu_informazioni_colonia info on (r.id_rel_stab_lp = info.id_relazione_stabilimento_linea_produttiva)
join opu_operatore o on r.id_opu_operatore=o.id
join opu_stabilimento s on s.id_operatore = o.id 
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
join comuni1 c on (c.id = r.comune)
where r.id_rel_stab_lp = (select id_proprietario from animale where microchip = microchip_to_get and trashed_date is null and data_cancellazione is null);

return rec;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getdaticolonia(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getdaticolonia(text) TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getdaticolonia(text) TO public;




-- Function: public_functions.getlistaregistrazionicane(text)

-- DROP FUNCTION public_functions.getlistaregistrazionicane(text);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionicane(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni;
tipologia_registrazione integer;
flag_prelievo boolean;
BEGIN
select false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false, false into  record_to_return;


open cursore for select wk.id_registrazione, c.flag_prelievo_dna  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join cane c on (a.id = c.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1);


LOOP

fetch cursore into tipologia_registrazione, flag_prelievo;
EXIT WHEN NOT FOUND; 

raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione

WHEN 24 then /* RICATTURA*/
record_to_return.ricattura := true;

WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;

WHEN 6 then /*PASSAPORTO*/
raise info 'entrato nel case when 6 val %', tipologia_registrazione;
record_to_return.passaporto := true;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;


WHEN 7 then /*Cessione*/
record_to_return.cessione := true;
raise info 'entrato nel case when 7 val %', tipologia_registrazione;

WHEN 26 THEN /*Controlli commerciali*/
record_to_return.controllComm := true;

WHEN 22 THEN /*controlli*/
record_to_return.controlli := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;

WHEN 15 THEN /*presa in carico da cessione*/
record_to_return.gestCessione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;


WHEN 8 THEN /*trasferimento fr*/
record_to_return.trasfRegione := true;

WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;

WHEN 17 THEN /*trasferimento*/
record_to_return.rientro := true;


WHEN 13 THEN
record_to_return.adozione := true;


WHEN 41 THEN  /* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

WHEN 50 THEN /* PRELIEVO DNA*/
if (flag_prelievo = false or flag_prelievo is NULL) then
record_to_return.prelievodna := true;
end if;

WHEN 46 THEN /* ADOZIONE FUORI ASL, FUORI REGIONE*/
record_to_return.adozionefa := true;


ELSE 

END CASE;




END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionicane(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO postgres;



-- Function: public_functions.getlistaregistrazionigatto(text)

-- DROP FUNCTION public_functions.getlistaregistrazionigatto(text);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionigatto(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni_gatto AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni_gatto;
tipologia_registrazione integer;
BEGIN
select false,false,false,false,false,false,false,false,false,false into  record_to_return;


open cursore for select wk.id_registrazione from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2);


LOOP

fetch cursore into tipologia_registrazione;
EXIT WHEN NOT FOUND; 

raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione


WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;



WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;


WHEN 19 THEN /*adozione*/
record_to_return.adozione := true;

WHEN 6 THEN /*passaporto*/
record_to_return.passaporto := true;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 24 THEN /*ricattura*/
record_to_return.ricattura := true;

WHEN 46 THEN /*adozione fuori asl / fuori regione*/
record_to_return.adozionefa := true;

ELSE 

END CASE;

/* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionigatto(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO postgres;



-- Type: public_functions.lista_registrazioni

-- DROP TYPE public_functions.lista_registrazioni;

CREATE TYPE public_functions.lista_registrazioni AS
   (adozione boolean,
    ricattura boolean,
    cessione boolean,
    controllcomm boolean,
    controlli boolean,
    decesso boolean,
    furto boolean,
    gestcessione boolean,
    passaporto boolean,
    presacessione boolean,
    reimmissione boolean,
    restituisci boolean,
    rientro boolean,
    ritrovamento boolean,
    ritrovamentosmarrnondenunciato boolean,
    smarrimento boolean,
    trasfcanile boolean,
    trasfpropcanile boolean,
    trasfregione boolean,
    sterilizzazione boolean,
    trasferimento boolean,
    prelievodna boolean,
    adozionefa boolean,
    trasfresidenzaprop boolean);
ALTER TYPE public_functions.lista_registrazioni
  OWNER TO postgres;





-- Function: public_functions.getlistaregistrazionicane(text)

-- DROP FUNCTION public_functions.getlistaregistrazionicane(text);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionicane(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni;
tipologia_registrazione integer;
flag_prelievo boolean;
BEGIN
select false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false, false into  record_to_return;


open cursore for select wk.id_registrazione, c.flag_prelievo_dna  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join cane c on (a.id = c.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1);


LOOP

fetch cursore into tipologia_registrazione, flag_prelievo;
EXIT WHEN NOT FOUND; 

raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione

WHEN 24 then /* RICATTURA*/
record_to_return.ricattura := true;

WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;

WHEN 6 then /*PASSAPORTO*/
raise info 'entrato nel case when 6 val %', tipologia_registrazione;
record_to_return.passaporto := true;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;


WHEN 7 then /*Cessione*/
record_to_return.cessione := true;
raise info 'entrato nel case when 7 val %', tipologia_registrazione;

WHEN 26 THEN /*Controlli commerciali*/
record_to_return.controllComm := true;

WHEN 22 THEN /*controlli*/
record_to_return.controlli := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;

WHEN 15 THEN /*presa in carico da cessione*/
record_to_return.gestCessione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;


WHEN 8 THEN /*trasferimento fr*/
record_to_return.trasfRegione := true;

WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;

WHEN 17 THEN /*trasferimento*/
record_to_return.rientro := true;


WHEN 13 THEN
record_to_return.adozione := true;


WHEN 41 THEN  /* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

WHEN 50 THEN /* PRELIEVO DNA*/
if (flag_prelievo = false or flag_prelievo is NULL) then
record_to_return.prelievodna := true;
end if;

WHEN 46 THEN /* ADOZIONE FUORI ASL, FUORI REGIONE*/
record_to_return.adozionefa := true;

WHEN 43 THEN /* MODIFICA RESIDENZA PROPRIETARIO O DETENTORE*/
record_to_return.trasfresidenzaprop := true;


ELSE 

END CASE;




END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionicane(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO postgres;


-- Type: public_functions.lista_registrazioni_gatto

-- DROP TYPE public_functions.lista_registrazioni_gatto;

CREATE TYPE public_functions.lista_registrazioni_gatto AS
   (adozione boolean,
    decesso boolean,
    furto boolean,
    ricattura boolean,
    ritrovamento boolean,
    ritrovamentosmarrnondenunciato boolean,
    smarrimento boolean,
    trasferimento boolean,
    passaporto boolean,
    reimmissione boolean,
    sterilizzazione boolean,
    adozionefa boolean,
    trasfresidenzaprop boolean,
    trasfregione boolean,
    cessione boolean);
ALTER TYPE public_functions.lista_registrazioni_gatto
  OWNER TO postgres;



-- Function: public_functions.getlistaregistrazionigatto(text)

-- DROP FUNCTION public_functions.getlistaregistrazionigatto(text);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionigatto(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni_gatto AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni_gatto;
tipologia_registrazione integer;
BEGIN
select false,false,false,false,false,false,false,false,false,false into  record_to_return;


open cursore for select wk.id_registrazione from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2);


LOOP

fetch cursore into tipologia_registrazione;
EXIT WHEN NOT FOUND; 

raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione


WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;



WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;


WHEN 19 THEN /*adozione*/
record_to_return.adozione := true;

WHEN 6 THEN /*passaporto*/
record_to_return.passaporto := true;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 24 THEN /*ricattura*/
record_to_return.ricattura := true;

WHEN 46 THEN /*adozione fuori asl / fuori regione*/
record_to_return.adozionefa := true;

WHEN 43 THEN /*modifica residenza proprietario o detentore*/
record_to_return.trasfresidenzaprop := true;

WHEN 7 THEN /*Cessione*/
record_to_return.cessione := true;

WHEN 8 THEN /*trasferimento fuori regione*/
record_to_return.trasfregione := true;

ELSE 

END CASE;

/* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionigatto(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO postgres;


select max(id) from capability

INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (407, 'Universita', NULL, 'ACCETTAZIONE->DETAIL->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 407, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (408, 'Universita', NULL, 'ACCETTAZIONE->LIST->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 408, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (409, 'Universita', NULL, 'ACCETTAZIONE->MAIN->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 409, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (410, 'Universita', NULL, 'ACCETTAZIONE->ADD->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 410, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (411, 'Universita', NULL, 'ACCETTAZIONE->DELETE->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 411, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (412, 'Universita', NULL, 'ACCETTAZIONE->EDIT->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 412, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (413, 'IZSM', NULL, 'ACCETTAZIONE->DETAIL->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 413, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (414, 'IZSM', NULL, 'ACCETTAZIONE->LIST->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 414, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (415, 'IZSM', NULL, 'ACCETTAZIONE->MAIN->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 415, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (416, 'IZSM', NULL, 'ACCETTAZIONE->ADD->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 416, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (417, 'IZSM', NULL, 'ACCETTAZIONE->DELETE->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 417, 'w');
INSERT INTO capability (id, category_name, secureobject_name, subject_name) VALUES (418, 'IZSM', NULL, 'ACCETTAZIONE->EDIT->MAIN');
INSERT INTO capability_permission (capabilities_id, permissions_name) VALUES ( 418, 'w');




CREATE OR REPLACE FUNCTION public_functions.inseriscismarrimento(microchip_to_get text, data_smarrimento timestamp without time zone, luogo text, note character varying, flag boolean, importo double precision, user_username character varying, caller integer)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idProprietario integer;
 idDetentore integer;
 idProssimoStato integer;
 cursore refcursor;
 cursore1 refcursor;
 idEvento integer;
 idAslUtente integer;
 idUtente integer;
 
 BEGIN
 open cursore for select id, id_asl_riferimento, id_specie, stato, id_proprietario, id_detentore from animale where microchip = microchip_to_get; 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato, idProprietario, idDetentore;
EXIT WHEN NOT FOUND; 


open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 


/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale, id_stato_originale, note, id_proprietario_corrente,
  id_detentore_corrente,
  origine_registrazione,
  inserimento_registrazione_forzato)
    VALUES (idAnimale, microchip_to_get, now(), now(), idUtente, idUtente, 
            11, idAslUtente, idSpecie, idStato, note, idProprietario, idDetentore, caller, false) RETURNING id_evento INTO idEvento;   

INSERT INTO evento_smarrimento(
            id_evento, data_smarrimento, luogo_smarrimento, flag_importo_smarrimento, 
            importo_smarrimento)
    VALUES ( idEvento, data_smarrimento, luogo, flag, 
            importo);
Select id_prossimo_stato into idProssimoStato from registrazioni_wk where id_stato = idStato and id_registrazione = 11;

Update animale set stato = idProssimoStato where microchip = microchip_to_get;

end loop;
end loop;
   



return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inseriscismarrimento(text, timestamp without time zone, text, character varying, boolean, double precision, character varying, integer)
  OWNER TO postgres;




  -- Function: public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer)

-- DROP FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer);

CREATE OR REPLACE FUNCTION public_functions.inseriscidecesso(microchip_to_get character varying, data timestamp without time zone, idcausadecesso integer, flagdatapresunta boolean, codiceistatcomunedecesso character varying, indirizzodecesso character varying, note character varying, user_username character varying, caller integer)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idProprietario integer;
 idDetentore integer;
 idProssimoStato integer;
 cursore refcursor;
 cursore1 refcursor;
 cursore2 refcursor;
 idEvento integer;

 /*utente*/
 idUtente integer;
 idAslUtente integer;


 /*indirizzo*/
 idProvincia integer;
 idComune integer;

 
 BEGIN

 /*Dati animale*/
 open cursore for select id, id_asl_riferimento, id_specie, stato, id_proprietario, id_detentore from animale where microchip = microchip_to_get; 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato, idProprietario, idDetentore;
EXIT WHEN NOT FOUND; 


/*dati utente*/
 
open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 

/*dati indirizzo decesso*/

open cursore2 for select id, cod_provincia from comuni1 where istat = codiceistatcomunedecesso;
LOOP
fetch cursore2 into idComune, idProvincia;
EXIT WHEN NOT FOUND; 


raise info 'datiutente % % ', idUtente, idAslUtente;

/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale, id_stato_originale, note, id_proprietario_corrente, id_detentore_corrente,  origine_registrazione,
	inserimento_registrazione_forzato)
    VALUES (idAnimale, microchip_to_get, now(), now(), idUtente, idUtente, 
            9, idAslUtente, idSpecie, idStato, note, idProprietario, idDetentore, caller, false) RETURNING id_evento INTO idEvento;   

INSERT INTO evento_decesso(
            id_evento, data_decesso, flag_decesso_presunto, id_causa_decesso,   id_provincia_decesso,  id_comune_decesso, indirizzo_decesso)
    VALUES ( idEvento, data,  
            flagDataPresunta, idCausaDecesso, idProvincia, idComune, indirizzodecesso);





Select id_prossimo_stato into idProssimoStato from registrazioni_wk where id_stato = idStato and id_registrazione = 9;

Update animale set stato = idProssimoStato, flag_decesso=true where microchip = microchip_to_get;



 END LOOP;
  END LOOP;
  END LOOP;


return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer)
  OWNER TO postgres;




-- Function: public_functions.inseriscipassaporto(text, timestamp without time zone, character varying, character varying, text, integer)

-- DROP FUNCTION public_functions.inseriscipassaporto(text, timestamp without time zone, character varying, character varying, text, integer);

CREATE OR REPLACE FUNCTION public_functions.inseriscipassaporto(microchip_to_get text, data timestamp without time zone, numeropassaporto character varying, note character varying, user_username text, caller integer)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idProprietario integer;
 idDetentore integer;
 idProssimoStato integer;
 cursore refcursor;
 cursore1 refcursor;
 idEvento integer;

 idUtente integer;
 idAslUtente integer;
 
 BEGIN
 
 open cursore for select id, id_asl_riferimento, id_specie, stato, id_proprietario, id_detentore from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null and trashed_date is null; 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato, idProprietario, idDetentore;
EXIT WHEN NOT FOUND; 

open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 


/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale, id_proprietario_corrente, id_detentore_corrente, origine_registrazione, inserimento_registrazione_forzato)
    VALUES (idAnimale, microchip_to_get, now(), now(), idUtente, idUtente, 
            6, idAslUtente, idSpecie, idProprietario, idDetentore, caller, false) RETURNING id_evento INTO idEvento;   

    INSERT INTO evento_rilascio_passaporto(
            id_evento, data_rilascio_passaporto, numero_passaporto)
    VALUES (idEvento, data, numeroPassaporto);





Update animale set numero_passaporto = numeroPassaporto, data_rilascio_passaporto = data  where (microchip = microchip_to_get or tatuaggio = microchip_to_get);


end loop;  

end loop;

return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inseriscipassaporto(text, timestamp without time zone, character varying, character varying, text, integer)
  OWNER TO postgres;
  
  
--Modifiche a lista anomalie necroscopico richieste da Rosato nel 2013 prendendo spunto dall'EOG
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 137, 'Anomalie scheletriche congenite', 1150, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 138, 'Nanismo', 1160, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 139, 'Acromegalia', 1170, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 140, 'Anomalie scheletriche acquisite', 1180, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 141, 'Lordosi', 1200, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 142, 'Cifosi', 1210, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 143, 'Pectum excavatum', 1220, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 144, 'Callo osseo esito frattura', 1230, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 145, 'Fratture esposte', 1240, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 146, 'Fratture', 1250, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 147, 'Rachitismo', 1260, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 148, 'Amputazione', 1270, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 149, 'Arto anteriore/superiore dx', 1260, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 150, 'Arto anteriore/superiore sin', 1260, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 151, 'Arto posteriore/inferiore sin', 1260, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 152, 'Arto posteriore/inferiore destro', 1260, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 153, 'Asimmetrie scheletriche', 1260, true );

--Associo le nuove sottovoci all'organo
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 137);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 138);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 139);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 140);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 141);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 142);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 143);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 144);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 145);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 146);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 147);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 148);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 149);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 150);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 151);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 152);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Sviluppo scheletrico%'), 153);

insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 154, 'Congeste', 1440, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 155, 'Mucose slavate', 1450, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 156, 'Mucose pallide', 1460, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 157, 'Mucose porcellanacee', 1470, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 158, 'Mucose itteriche', 1480, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 159, 'Mucose cianotiche', 1490, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 160, 'Petecchie', 1500, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 161, 'Ecchimosi', 1510, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 162, 'Soffusioni', 1520, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 163, 'Secchezza mucose', 1530, true );

--Associo le nuove sottovoci all'organo
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 154);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 155);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 156);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 157);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 158);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 159);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 160);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 161);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 162);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), 163);
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), (select id from lookup_autopsia_patologie_prevalenti where description ilike '%pigmentazioni%'));
insert into organi_patologieprevalenti(organo, patologia_prevalente)  values((select id from lookup_autopsia_organi where description ilike '%Mucose apparenti%'), (select id from lookup_autopsia_patologie_prevalenti where description ilike '%post-mortali%'));

insert into lookup_autopsia_organi( id, description, level_sde, enabled, tessuto, enabled_sde, level, cani,gatti, uccelli, mammiferi, rettili ) values( 77,  'Linfonodi esplorabili', 155, true, false , true, 63, true, true, true, true, true);

--Inserisco le nuove sottovoci
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 164, 'Linfoadenomegalia generalizzata', 1540, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 165, 'Linfoadenomegalia	- Perifaringei dx', 1550, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 166, 'Linfoadenomegalia	- Prescapolari sx', 1560, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 167, 'Linfoadenomegalia	- Poplitei sx	', 1570, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 168, 'Linfoadenomegalia	- Prescapolari dx', 1580, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 169, 'Linfoadenomegalia	- Perfaringei sx', 1590, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 170, 'Linfoadenomegalia	- Poplitei dx', 1600, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 171, 'Linfonodi a superficie irregolare - Perifaringei dx', 1610, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 172, 'Linfonodi a superficie irregolare - Prescapolari dx', 1620, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 173, 'Linfonodi a superficie irregolare - Perifaringei sx', 1630, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 174, 'Linfonodi a superficie irregolare - Poplitei dx', 1640, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 175, 'Linfonodi a superficie irregolare - Poplitei sx', 1650, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 176, 'Linfonodi a superficie irregolare - Prescapolari sx', 1660, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 177, 'Linfonodi non mobili - Poplitei dx', 1670, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 178, 'Linfonodi non mobili - Prescapolari sx', 1680, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 179, 'Linfonodi non mobili - Poplitei sx	', 1690, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 180, 'Linfonodi non mobili - Perifaringei sx', 1700, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 181, 'Linfonodi non mobili - Prescapolari dx', 1710, true );
insert into lookup_autopsia_patologie_prevalenti( id, description, level, enabled ) values( 182, 'Linfonodi non mobili - Perifaringei dx', 1720, true );

--Associo le nuove sottovoci all'organo
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 164);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 165);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 166);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 167);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 168);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 169);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 170);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 171);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 172);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 173);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 174);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 175);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 176);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 177);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 178);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 179);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 180);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 181);
insert into organi_patologieprevalenti(organo, patologia_prevalente)   values(77, 182);

--Fine Modifiche a lista anomalie necroscopico richieste da Rosato nel 2013 prendendo spunto dall'EOG



alter table animale ADD tatuaggio character varying(64);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionicane(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni AS
$BODY$
DECLARE  
cursore	refcursor;
cursore2 refcursor;
record_to_return public_functions.lista_registrazioni;
tipologia_registrazione integer;
flag_prelievo boolean;
data_vaccinazione_antirabbia timestamp without time zone;
numero_giorni_da_vaccinazione integer;
passaporto_corrente character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false, false into  record_to_return;


open cursore for select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join cane c on (a.id = c.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1);


LOOP

fetch cursore into tipologia_registrazione, flag_prelievo, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND;


/*Calcolo giorni da ultima vaccinazione antirabbia*/
 select (to_date(to_char(current_timestamp, 'YYYY/MM/DD'),  'YYYY/MM/DD') - 
to_date( to_char( data_vaccinazione_antirabbia, 'YYYY/MM/DD'), 'YYYY/MM/DD')) into  numero_giorni_da_vaccinazione;

 


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione

WHEN 24 then /* RICATTURA*/
record_to_return.ricattura := true;

WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;

WHEN 6 then /*PASSAPORTO*/
raise info 'entrato nel case when 6 val %', tipologia_registrazione;
if ((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365) then
record_to_return.passaporto := true;
end if;

WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;


WHEN 7 then /*Cessione*/
record_to_return.cessione := true;
raise info 'entrato nel case when 7 val %', tipologia_registrazione;

WHEN 26 THEN /*Controlli commerciali*/
record_to_return.controllComm := true;

WHEN 22 THEN /*controlli*/
record_to_return.controlli := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;

WHEN 15 THEN /*presa in carico da cessione*/
record_to_return.gestCessione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;


WHEN 8 THEN /*trasferimento fr*/
record_to_return.trasfRegione := true;

WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;

WHEN 17 THEN /*trasferimento*/
record_to_return.rientro := true;


WHEN 13 THEN
record_to_return.adozione := true;


WHEN 41 THEN  /* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

WHEN 50 THEN /* PRELIEVO DNA*/
if (flag_prelievo = false or flag_prelievo is NULL) then
record_to_return.prelievodna := true;
end if;

WHEN 46 THEN /* ADOZIONE FUORI ASL, FUORI REGIONE*/
record_to_return.adozionefa := true;

WHEN 43 THEN /* MODIFICA RESIDENZA PROPRIETARIO O DETENTORE*/
record_to_return.trasfresidenzaprop := true;


ELSE 

END CASE;



--END LOOP;
END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionicane(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO postgres;






-- Function: public_functions.getlistaregistrazionigatto(text)

-- DROP FUNCTION public_functions.getlistaregistrazionigatto(text);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionigatto(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni_gatto AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni_gatto;
tipologia_registrazione integer;
data_vaccinazione_antirabbia timestamp without time zone;
numero_giorni_da_vaccinazione integer;
passaporto_corrente character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false into  record_to_return;


open cursore for select wk.id_registrazione, a.vaccino_data, a.numero_passaporto from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2);


LOOP

fetch cursore into tipologia_registrazione, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND; 


/*Calcolo giorni da ultima vaccinazione antirabbia*/
select (to_date(to_char(current_timestamp, 'YYYY/MM/DD'),  'YYYY/MM/DD') - 
to_date( to_char( data_vaccinazione_antirabbia, 'YYYY/MM/DD'), 'YYYY/MM/DD')) into  numero_giorni_da_vaccinazione;


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione


WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;



WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;


WHEN 19 THEN /*adozione*/
record_to_return.adozione := true;

WHEN 6 THEN /*passaporto*/
if ((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365) then
record_to_return.passaporto := true;
end if;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 24 THEN /*ricattura*/
record_to_return.ricattura := true;

WHEN 46 THEN /*adozione fuori asl / fuori regione*/
record_to_return.adozionefa := true;

WHEN 43 THEN /*modifica residenza proprietario o detentore*/
record_to_return.trasfresidenzaprop := true;

WHEN 7 THEN /*Cessione*/
record_to_return.cessione := true;

WHEN 8 THEN /*trasferimento fuori regione*/
record_to_return.trasfregione := true;

ELSE 

END CASE;

/* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionigatto(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO postgres;

/* Necroscopia non effettuabile*/
ALTER TABLE animale ADD necroscopia_non_effettuabile boolean DEFAULT false

/*update delle colonne della tab accettazione per la nuova gestione dei dati proprietario*/
ALTER TABLE accettazione
ALTER COLUMN proprietario_codice_fiscale TYPE character varying(64)

ALTER TABLE accettazione
ALTER COLUMN richiedente_codice_fiscale TYPE character varying(64)


/*dbi per la nuova gestione dei dati del proprietario. (da lanciare sul db di bdu)*/
-- Function: public_functions.getdatiproprietarioanimale(text)

-- DROP FUNCTION public_functions.getdatiproprietarioanimale(text);

CREATE OR REPLACE FUNCTION public_functions.getdatiproprietarioanimale(microchip_to_get text)
  RETURNS public_functions.dati_proprietario_animale_to_vam AS
$BODY$
DECLARE  
 relazione_privato CONSTANT integer := 1; /* PROPRIETARIO DI TIPO PRIVATO*/
 relazione_canile  CONSTANT integer := 5; /* PROPRIETARIO DI TIPO CANILE*/
 relazione_sindaco CONSTANT integer := 3; /* PROPRIETARIO DI TIPO SINDACO*/
 relazione_colonia CONSTANT integer := 9; /* PROPRIETARIO DI TIPO COLONIA*/
 relazione_sindaco_fr CONSTANT integer := 7; /* PROPRIETARIO DI TIPO SINDACO FR*/
 relazione_operatore_commerciale CONSTANT integer := 6; /* PROPRIETARIO OPERATORE COMMERCIALE*/
 relazione_importatore CONSTANT integer := 4; /* PROPRIETARIO IMPORTATORE*/
 tipologia integer;
 rec public_functions.dati_proprietario_animale_to_vam;  
BEGIN 
/*Recupero la tipologia di linea produttiva */
Select id_linea_produttiva into tipologia from animale 
join opu_relazione_stabilimento_linee_produttive rel on(animale.id_proprietario = rel.id) where (animale.microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null; 

raise info 'dati % ', tipologia;
CASE tipologia

WHEN relazione_privato then

/* indirizzo dell'operatore, corrispondente all'indirizzo del sogg fisico*/
  Select r.id, sog.nome, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,   indirizzo.cap,  
o.trashed_date, s.id_asl,sog.telefono , 'Privato' as tipo into rec from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);

WHEN relazione_canile THEN
/* Sede operativa */
Select r.id, o.ragione_sociale, o.partita_iva as cognome, sog.nome ||' '|| sog.cognome ||' '|| sog.codice_fiscale as codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,   indirizzo.cap, 
o.trashed_date, s.id_asl, sog.telefono , 'Canile' as tipo  into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);

WHEN relazione_sindaco THEN 
/* indirizzo dell'operatore, corrispondente all'indirizzo del sogg fisico*/
Select r.id, sog.nome, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,  indirizzo.cap,   
o.trashed_date, s.id_asl, sog.telefono , 'Sindaco' as tipo  into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_relazione_operatore_sede relindirizzo on (o.id = relindirizzo.id_operatore)
join opu_indirizzo indirizzo on (relindirizzo.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);

WHEN relazione_colonia THEN
/* Sede operativa  corrispondente a indirizzo colonia*/
Select r.id, info.nr_protocollo, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,  indirizzo.cap,  
o.trashed_date, s.id_asl,sog.telefono , 'Colonia' as tipo  into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
join opu_informazioni_colonia info on (r.id = info.id_relazione_stabilimento_linea_produttiva)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);

WHEN relazione_sindaco_fr THEN 
/* indirizzo dell'operatore, corrispondente all'indirizzo del sogg fisico*/
Select r.id, sog.nome, sog.cognome, sog.codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,  indirizzo.cap, 
o.trashed_date, s.id_asl, sog.telefono  , 'Sindaco' as tipo into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_relazione_operatore_sede relindirizzo on (o.id = relindirizzo.id_operatore)
join opu_indirizzo indirizzo on (relindirizzo.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);

WHEN relazione_operatore_commerciale THEN
/* sede operativa*/
Select r.id, o.ragione_sociale, o.partita_iva as cognome, sog.nome ||' '|| sog.cognome ||' '|| sog.codice_fiscale as codice_fiscale, sog.documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via, indirizzo.cap,
o.trashed_date, s.id_asl, sog.telefono, 'Operatore Commerciale' as tipo into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);

WHEN relazione_importatore THEN
/* sede operativa*/
Select r.id, o.ragione_sociale, o.partita_iva as cognome, sog.nome ||' '|| sog.cognome ||' '|| sog.codice_fiscale as codice_fiscale, r.telefono2 as documento_identita, c.nome as comune, p.description as provincia, indirizzo.nazione, indirizzo.via,  indirizzo.cap,
o.trashed_date, s.id_asl,  r.telefono1 as telefono , 'Importatore' as tipo into rec
from  opu_operatore o  join opu_stabilimento s on s.id_operatore = o.id 
join opu_relazione_stabilimento_linee_produttive r on r.id_stabilimento = s.id
join opu_rel_operatore_soggetto_fisico rels on ( o.id = rels.id_operatore )
join opu_soggetto_fisico sog on (rels.id_soggetto_fisico = sog.id)
join opu_indirizzo indirizzo on (s.id_indirizzo = indirizzo.id)
left join comuni1 c on (c.id = indirizzo.comune)
left join lookup_province p on (c.cod_provincia::integer = p.code)
where r.id = (select id_proprietario from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and data_cancellazione is null);

ELSE 

Select null into rec;

END CASE;
return rec;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getdatiproprietarioanimale(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getdatiproprietarioanimale(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getdatiproprietarioanimale(text) TO postgres;

/*aggiunta della colonna data_esito alla tabella autopsia*/
ALTER TABLE autopsia ADD COLUMN data_esito timestamp without time zone


-- Function: public_functions.getdaticane(text)

-- DROP FUNCTION public_functions.getdaticane(text);

CREATE OR REPLACE FUNCTION public_functions.getdaticane(microchip_to_get text)
  RETURNS public_functions.dati_animale_to_vam_new AS
$BODY$
DECLARE  
 rec public_functions.dati_animale_to_vam_new;  
BEGIN 


select a.id, a.sesso, a.id_razza, a.id_tipo_mantello, a.id_taglia, r.description, m.description,t.description,
a.note, a.segni_particolari, a.data_nascita, evento_decesso.data_decesso, evento_decesso.id_causa_decesso, l_decesso.description, case a.passaporto_numero when '' then false else true end, a.passaporto_numero,
lookup_tipologia_stato.description, a.flag_sterilizzazione,
cane.flag_reimmesso, evento_decesso.flag_decesso_presunto, a.flag_data_nascita_presunta,
a.trashed_date, evento_sterilizzazione.data_sterilizzazione, coalesce(asl_sterilizz.description, concat (c.namelast, ', ',c.namefirst)  ), 
a.microchip, tatuaggio, a.vaccino_data
into rec
from animale a left join cane on (a.id = cane.id_animale) 
left join evento e1 on (a.id = e1.id_animale and e1.id_tipologia_evento = 9 and e1.data_cancellazione is null )
left join evento_decesso on (e1.id_evento = evento_decesso.id_evento)
left join evento e2 on (a.id = e2.id_animale and e2.id_tipologia_evento = 2 and e2.data_cancellazione is null )
left join evento_sterilizzazione on (e2.id_evento = evento_sterilizzazione.id_evento)
left join contact c on (c.user_id = evento_sterilizzazione.id_soggetto_sterilizzante )
left join lookup_asl_rif asl_sterilizz on (asl_sterilizz.code = evento_sterilizzazione.id_soggetto_sterilizzante)
left join lookup_tipologia_decesso l_decesso on (evento_decesso.id_causa_decesso = l_decesso.code)
left join lookup_tipologia_stato on (a.stato = lookup_tipologia_stato.code)
left join lookup_razza r on (a.id_razza = r.code)
left join lookup_mantello m on (a.id_tipo_mantello = m.code)
left join lookup_taglia t on (a.id_taglia = t.code)
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.id_specie=1 and a.data_cancellazione is null and a.trashed_date is null;


return rec;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getdaticane(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getdaticane(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getdaticane(text) TO postgres;



-- Function: public_functions.getdatigatto(text)

-- DROP FUNCTION public_functions.getdatigatto(text);

CREATE OR REPLACE FUNCTION public_functions.getdatigatto(microchip_to_get text)
  RETURNS public_functions.dati_animale_to_vam_new AS
$BODY$
DECLARE  
 rec public_functions.dati_animale_to_vam_new;  
BEGIN 

select a.id, a.sesso, a.id_razza, a.id_tipo_mantello, a.id_taglia, r.description, m.description,t.description,
a.note, a.segni_particolari, a.data_nascita, evento_decesso.data_decesso, evento_decesso.id_causa_decesso, l_decesso.description, case a.passaporto_numero when '' then false else true end, a.passaporto_numero,lookup_tipologia_stato.description, a.flag_sterilizzazione,
false, evento_decesso.flag_decesso_presunto, a.flag_data_nascita_presunta,
a.trashed_date, evento_sterilizzazione.data_sterilizzazione, coalesce(asl_sterilizz.description, concat (c.namelast, ', ',c.namefirst)  ), 
a.microchip, tatuaggio, a.vaccino_data into rec
from animale a left join gatto on (a.id = gatto.id_animale) 
left join evento e1 on (a.id = e1.id_animale and e1.id_tipologia_evento = 9 and e1.data_cancellazione is null)
left join evento_decesso on (e1.id_evento = evento_decesso.id_evento)
left join lookup_tipologia_decesso l_decesso on (evento_decesso.id_causa_decesso = l_decesso.code)
left join evento e2 on (a.id = e2.id_animale and e2.id_tipologia_evento = 2 and e2.data_cancellazione is null)
left join evento_sterilizzazione on (e2.id_evento = evento_sterilizzazione.id_evento)
left join contact c on (c.user_id = evento_sterilizzazione.id_soggetto_sterilizzante )
left join lookup_asl_rif asl_sterilizz on (asl_sterilizz.code = evento_sterilizzazione.id_soggetto_sterilizzante)
left join lookup_tipologia_stato on (a.stato = lookup_tipologia_stato.code)
left join lookup_razza r on (a.id_razza = r.code)
left join lookup_mantello m on (a.id_tipo_mantello = m.code)
left join lookup_taglia t on (a.id_taglia = t.code)
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.id_specie=2 and a.data_cancellazione is null and a.trashed_date is null;


return rec;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getdatigatto(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getdatigatto(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getdatigatto(text) TO postgres;


alter table utenti_super
add  access_position_lat double precision,
add  access_position_lon double precision,
add access_position_err text

alter table utenti
add  access_position_lat double precision,
add  access_position_lon double precision,
add access_position_err text


alter table utenti_operazioni
add  access_position_lat double precision,
add  access_position_lon double precision,
add access_position_err text;


INSERT INTO lookup_operazioni_accettazione(
            id,alta_specialita_chirurgica, approfondimenti, approfondimento_diagnostico_medicina, 
            canina, description, diagnostica_strumentale, effettuabile_da_morto, 
            effettuabile_fuori_asl, enabled, felina, inbdr, level, richiesta_prelievi_malattie_infettive, 
            scelta_asl, sinantropi, effettuabile_fuori_asl_morto, effettuabile_da_vivo, 
            enabled_default, obbligo_cc, id_bdr, enabled_in_page, intra_fuori_asl)
    VALUES (58, false, false, false, 
            true, 'Rinnovo/smarrimento Passaporto', false, false, 
            false, true, true, true, 75, false, 
            false, false, false, true, 
            false, false, 48, true, false);
            
            
-- Function: public_functions.getlistaregistrazionicane(text)

-- DROP FUNCTION public_functions.getlistaregistrazionicane(text);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionicane(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni AS
$BODY$
DECLARE  
cursore	refcursor;
cursore2 refcursor;
record_to_return public_functions.lista_registrazioni;
tipologia_registrazione integer;
flag_prelievo boolean;
data_vaccinazione_antirabbia timestamp without time zone;
/*numero_giorni_da_vaccinazione integer;*/
passaporto_corrente character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false, false into  record_to_return;


open cursore for select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join cane c on (a.id = c.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1);


LOOP

fetch cursore into tipologia_registrazione, flag_prelievo, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND;


/*Calcolo giorni da ultima vaccinazione antirabbia*/
 /*select (to_date(to_char(current_timestamp, 'YYYY/MM/DD'),  'YYYY/MM/DD') - 
to_date( to_char( data_vaccinazione_antirabbia, 'YYYY/MM/DD'), 'YYYY/MM/DD')) into  numero_giorni_da_vaccinazione;*/

 


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione

WHEN 24 then /* RICATTURA*/
record_to_return.ricattura := true;

WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;

WHEN 48 then /*FURTO*/
raise info 'entrato nel case when 48 val %', tipologia_registrazione;
record_to_return.rinnovo_passaporto := true;

WHEN 6 then /*PASSAPORTO*/
raise info 'entrato nel case when 6 val %', tipologia_registrazione;
/*if ((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/ 
if (passaporto_corrente is null or passaporto_corrente = '')
then
record_to_return.passaporto := true;
end if;

WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;


WHEN 7 then /*Cessione*/
record_to_return.cessione := true;
raise info 'entrato nel case when 7 val %', tipologia_registrazione;

WHEN 26 THEN /*Controlli commerciali*/
record_to_return.controllComm := true;

WHEN 22 THEN /*controlli*/
record_to_return.controlli := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;

WHEN 15 THEN /*presa in carico da cessione*/
record_to_return.gestCessione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;


WHEN 8 THEN /*trasferimento fr*/
record_to_return.trasfRegione := true;

WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;

WHEN 17 THEN /*trasferimento*/
record_to_return.rientro := true;


WHEN 13 THEN
record_to_return.adozione := true;


WHEN 41 THEN  /* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

WHEN 50 THEN /* PRELIEVO DNA*/
if (flag_prelievo = false or flag_prelievo is NULL) then
record_to_return.prelievodna := true;
end if;

WHEN 46 THEN /* ADOZIONE FUORI ASL, FUORI REGIONE*/
record_to_return.adozionefa := true;

WHEN 43 THEN /* MODIFICA RESIDENZA PROPRIETARIO O DETENTORE*/
record_to_return.trasfresidenzaprop := true;


ELSE 

END CASE;



--END LOOP;
END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionicane(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO postgres;


-- Function: public_functions.getlistaregistrazionigatto(text)

-- DROP FUNCTION public_functions.getlistaregistrazionigatto(text);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionigatto(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni_gatto AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni_gatto;
tipologia_registrazione integer;
data_vaccinazione_antirabbia timestamp without time zone;
--numero_giorni_da_vaccinazione integer;
passaporto_corrente character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false into  record_to_return;


open cursore for select wk.id_registrazione, a.vaccino_data, a.numero_passaporto from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2);

LOOP

fetch cursore into tipologia_registrazione, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND; 


/*Calcolo giorni da ultima vaccinazione antirabbia
select (to_date(to_char(current_timestamp, 'YYYY/MM/DD'),  'YYYY/MM/DD') - 
to_date( to_char( data_vaccinazione_antirabbia, 'YYYY/MM/DD'), 'YYYY/MM/DD')) into  numero_giorni_da_vaccinazione;*/


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione


WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;



WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;


WHEN 19 THEN /*adozione*/
record_to_return.adozione := true;

WHEN 48 THEN /*adozione*/
record_to_return.rinnovo_passaporto := true;

WHEN 6 THEN /*passaporto*/
if /*((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/
(passaporto_corrente is null or passaporto_corrente = '') then
record_to_return.passaporto := true;
end if;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 24 THEN /*ricattura*/
record_to_return.ricattura := true;

WHEN 46 THEN /*adozione fuori asl / fuori regione*/
record_to_return.adozionefa := true;

WHEN 43 THEN /*modifica residenza proprietario o detentore*/
record_to_return.trasfresidenzaprop := true;

WHEN 7 THEN /*Cessione*/
record_to_return.cessione := true;

WHEN 8 THEN /*trasferimento fuori regione*/
record_to_return.trasfregione := true;

ELSE 

END CASE;

/* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionigatto(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO postgres;





-- Type: public_functions.lista_registrazioni

-- DROP TYPE public_functions.lista_registrazioni;

CREATE TYPE public_functions.lista_registrazioni AS
   (adozione boolean,
    ricattura boolean,
    cessione boolean,
    controllcomm boolean,
    controlli boolean,
    decesso boolean,
    furto boolean,
    gestcessione boolean,
    passaporto boolean,
    rinnovo_passaporto boolean,
    presacessione boolean,
    reimmissione boolean,
    restituisci boolean,
    rientro boolean,
    ritrovamento boolean,
    ritrovamentosmarrnondenunciato boolean,
    smarrimento boolean,
    trasfcanile boolean,
    trasfpropcanile boolean,
    trasfregione boolean,
    sterilizzazione boolean,
    trasferimento boolean,
    prelievodna boolean,
    adozionefa boolean,
    trasfresidenzaprop boolean);
ALTER TYPE public_functions.lista_registrazioni
  OWNER TO postgres;





  -- Type: public_functions.lista_registrazioni_gatto

-- DROP TYPE public_functions.lista_registrazioni_gatto;

CREATE TYPE public_functions.lista_registrazioni_gatto AS
   (adozione boolean,
    decesso boolean,
    furto boolean,
    ricattura boolean,
    ritrovamento boolean,
    ritrovamentosmarrnondenunciato boolean,
    smarrimento boolean,
    trasferimento boolean,
    passaporto boolean,
    rinnovo_passaporto boolean,
    reimmissione boolean,
    sterilizzazione boolean,
    adozionefa boolean,
    trasfresidenzaprop boolean,
    trasfregione boolean,
    cessione boolean);
ALTER TYPE public_functions.lista_registrazioni_gatto
  OWNER TO postgres;

--Permessi per Cambio utente per Amministratore e Amministraotre Vam
insert into capability values(551,'Amministratore',    null,'AMMINISTRAZIONE->CAMBIO UTENTE->MAIN');
insert into capability values(552,'Amministratore VAM',null,'AMMINISTRAZIONE->CAMBIO UTENTE->MAIN');

insert into capability_permission values(551, 'w');
insert into capability_permission values(552, 'w');


-- Table: utenti_operazioni

-- DROP TABLE utenti_operazioni;

CREATE TABLE utenti_operazioni_modifiche
(
  id serial NOT NULL,
  modifiche text,
  bean text,
  operazione integer,
  CONSTRAINT utenti_operazioni__modifiche_pkey PRIMARY KEY (id ),
  CONSTRAINT fk11470e6a961bfafd FOREIGN KEY (operazione)
      REFERENCES utenti_operazioni(id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE utenti_operazioni_modifiche
  OWNER TO postgres;
GRANT ALL ON TABLE utenti_operazioni_modifiche TO postgres;
GRANT SELECT ON TABLE utenti_operazioni_modifiche TO usr_ro;

ALTER TABLE utenti_operazioni ADD COLUMN cc integer;

ALTER TABLE utenti_operazioni
  ADD CONSTRAINT fk11470e6a6bd84c92 FOREIGN KEY (cc)
      REFERENCES cartella_clinica (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;


-- Function: public_functions.get_id_tipo_ultima_registrazione(character varying)




CREATE TYPE public_functions.registrazione AS
   (id                    integer,
    id_tipo_reg           integer,
    cambio_detentore      boolean,
    origine_registrazione integer 
    );
ALTER TYPE public_functions.registrazione
  OWNER TO postgres;



-- DROP FUNCTION public_functions.getultimaregistrazione(character varying);
CREATE OR REPLACE FUNCTION public_functions.getultimaregistrazione(microchip_to_get character varying)
  RETURNS public_functions.registrazione AS
$BODY$
DECLARE  
 rec public_functions.registrazione;  
BEGIN 


select e.id_evento as id,
       t_reg.code as id_tipo_reg,
       case 
	     when t_reg.code = 12 then a.flag_detenuto_in_canile_dopo_ritrovamento
             when t_reg.code = 41 then r2.flag_ritrovamento_aperto 
             else false
       end as cambio_detentore,
       e.origine_registrazione       
into rec
from animale a
LEFT JOIN evento e ON e.id_animale = a.id 
LEFT JOIN lookup_tipologia_registrazione t_reg ON t_reg.code = e.id_tipologia_evento 
LEFT JOIN evento_ritrovamento                r  ON r.id_evento  = e.id_evento
LEFT JOIN evento_ritrovamento_non_denunciato r2 ON r2.id_evento = e.id_evento
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get)
order by e.entered desc
limit 1;

return rec;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getultimaregistrazione(character varying)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getultimaregistrazione(character varying) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getultimaregistrazione(character varying) TO postgres;

insert into lookup_operazioni_accettazione
(            description, inbdr, canina, felina, sinantropi,level, enabled,approfondimenti, approfondimento_diagnostico_medicina, 
  richiesta_prelievi_malattie_infettive,alta_specialita_chirurgica, diagnostica_strumentale, id_bdr, obbligo_cc  ) 
values( 
'Trasferimento a canile', false,  false,  false,      false,   10,    true,          false,                                false, 
                                  false,                     false,                   false, 31, false );
											
insert into lookup_operazioni_accettazione
(            description, inbdr, canina, felina, sinantropi,level, enabled,approfondimenti, approfondimento_diagnostico_medicina, 
  richiesta_prelievi_malattie_infettive,alta_specialita_chirurgica, diagnostica_strumentale, id_bdr, obbligo_cc  ) 
values( 
'Ritorno a proprietario', false,  false,  false,      false,   10,    true,          false,                                false, 
                                  false,                     false,                   false, 45, false );
                                  
                                  
DROP TYPE public_functions.lista_registrazioni;
DROP TYPE public_functions.lista_registrazioni_gatto;
DROP FUNCTION public_functions.getlistaregistrazionigatto(text);
DROP FUNCTION public_functions.getlistaregistrazionicane(text);


CREATE TYPE public_functions.lista_registrazioni_gatto AS
   (adozione boolean,
    decesso boolean,
    furto boolean,
    ricattura boolean,
    ritrovamento boolean,
    ritrovamentosmarrnondenunciato boolean,
    smarrimento boolean,
    trasferimento boolean,
    passaporto boolean,
    rinnovo_passaporto boolean,
    reimmissione boolean,
    sterilizzazione boolean,
    adozionefa boolean,
    trasfresidenzaprop boolean,
    trasfregione boolean,
    ritornoProprietario boolean,
    trasfCanile boolean,
    cessione boolean);
ALTER TYPE public_functions.lista_registrazioni_gatto
  OWNER TO postgres;

CREATE TYPE public_functions.lista_registrazioni AS
   (adozione boolean,
    ricattura boolean,
    cessione boolean,
    controllcomm boolean,
    controlli boolean,
    decesso boolean,
    furto boolean,
    gestcessione boolean,
    passaporto boolean,
    rinnovo_passaporto boolean,
    presacessione boolean,
    reimmissione boolean,
    restituisci boolean,
    rientro boolean,
    ritrovamento boolean,
    ritrovamentosmarrnondenunciato boolean,
    smarrimento boolean,
    trasfCanile boolean,
    ritornoProprietario boolean,
    trasfpropcanile boolean,
    trasfregione boolean,
    sterilizzazione boolean,
    trasferimento boolean,
    prelievodna boolean,
    adozionefa boolean,
    trasfresidenzaprop boolean);
ALTER TYPE public_functions.lista_registrazioni
  OWNER TO postgres;
  
CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionicane(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni AS
$BODY$
DECLARE  
cursore	refcursor;
cursore2 refcursor;
record_to_return public_functions.lista_registrazioni;
tipologia_registrazione integer;
flag_prelievo boolean;
data_vaccinazione_antirabbia timestamp without time zone;
/*numero_giorni_da_vaccinazione integer;*/
passaporto_corrente character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false, false into  record_to_return;


open cursore for select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join cane c on (a.id = c.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1);


LOOP

fetch cursore into tipologia_registrazione, flag_prelievo, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND;


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione

WHEN 24 then /* RICATTURA*/
record_to_return.ricattura := true;

WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;

WHEN 48 then /*FURTO*/
raise info 'entrato nel case when 48 val %', tipologia_registrazione;
record_to_return.rinnovo_passaporto := true;

WHEN 6 then /*PASSAPORTO*/
raise info 'entrato nel case when 6 val %', tipologia_registrazione;
/*if ((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/ 
if (passaporto_corrente is null or passaporto_corrente = '')
then
record_to_return.passaporto := true;
end if;

WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;


WHEN 7 then /*Cessione*/
record_to_return.cessione := true;
raise info 'entrato nel case when 7 val %', tipologia_registrazione;

WHEN 26 THEN /*Controlli commerciali*/
record_to_return.controllComm := true;

WHEN 22 THEN /*controlli*/
record_to_return.controlli := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;

WHEN 15 THEN /*presa in carico da cessione*/
record_to_return.gestCessione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;

WHEN 45 THEN /*restituzione a proprietario*/
record_to_return.ritornoProprietario := true;

WHEN 8 THEN /*trasferimento fr*/
record_to_return.trasfRegione := true;

WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;

WHEN 17 THEN /*trasferimento*/
record_to_return.rientro := true;


WHEN 13 THEN
record_to_return.adozione := true;


WHEN 41 THEN  /* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

WHEN 50 THEN /* PRELIEVO DNA*/
if (flag_prelievo = false or flag_prelievo is NULL) then
record_to_return.prelievodna := true;
end if;

WHEN 46 THEN /* ADOZIONE FUORI ASL, FUORI REGIONE*/
record_to_return.adozionefa := true;

WHEN 43 THEN /* MODIFICA RESIDENZA PROPRIETARIO O DETENTORE*/
record_to_return.trasfresidenzaprop := true;


ELSE 

END CASE;



--END LOOP;
END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionicane(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionicane(text) TO postgres;


CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionigatto(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni_gatto AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni_gatto;
tipologia_registrazione integer;
data_vaccinazione_antirabbia timestamp without time zone;
--numero_giorni_da_vaccinazione integer;
passaporto_corrente character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false into  record_to_return;


open cursore for select wk.id_registrazione, a.vaccino_data, a.numero_passaporto from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2);

LOOP

fetch cursore into tipologia_registrazione, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND; 


/*Calcolo giorni da ultima vaccinazione antirabbia
select (to_date(to_char(current_timestamp, 'YYYY/MM/DD'),  'YYYY/MM/DD') - 
to_date( to_char( data_vaccinazione_antirabbia, 'YYYY/MM/DD'), 'YYYY/MM/DD')) into  numero_giorni_da_vaccinazione;*/


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione


WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;



WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;


WHEN 19 THEN /*adozione*/
record_to_return.adozione := true;

WHEN 48 THEN /*adozione*/
record_to_return.rinnovo_passaporto := true;

WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;

WHEN 45 THEN /*restituzione a proprietario*/
record_to_return.ritornoProprietario := true;

WHEN 6 THEN /*passaporto*/
if /*((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/
(passaporto_corrente is null or passaporto_corrente = '') then
record_to_return.passaporto := true;
end if;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 24 THEN /*ricattura*/
record_to_return.ricattura := true;

WHEN 46 THEN /*adozione fuori asl / fuori regione*/
record_to_return.adozionefa := true;

WHEN 43 THEN /*modifica residenza proprietario o detentore*/
record_to_return.trasfresidenzaprop := true;

WHEN 7 THEN /*Cessione*/
record_to_return.cessione := true;

WHEN 8 THEN /*trasferimento fuori regione*/
record_to_return.trasfregione := true;

ELSE 

END CASE;

/* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionigatto(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO postgres;




insert into permessi_funzione(nome) values('STORICO');
insert into permessi_subfunzione(nome,id_funzione) values('MAIN',18);
insert into permessi_subfunzione(nome,id_funzione) values('DETAIL',18);
insert into permessi_gui(nome,id_subfunzione) values('MAIN',81);
insert into permessi_gui(nome,id_subfunzione) values('MAIN',82);

insert into subject values('STORICO->MAIN->MAIN');
insert into subject values('STORICO->DETAIL->MAIN');

insert into capability(category_name, subject_name) values('Amministratore',			'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('Ambulatorio - Veterinario', 	'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('Ambulatorio - Tecnico di Supporto', 'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('Ambulatorio - Amministrativo',	'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('IZSM',				'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('Sinantropi',			'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('Referente Asl',			'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('Ambulatorio - Veterinario 3',	'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('Universita',			'STORICO->MAIN->MAIN');
insert into capability(category_name, subject_name) values('Amministratore',			'STORICO->DETAIL->MAIN');
insert into capability(category_name, subject_name) values('Referente Asl',			'STORICO->DETAIL->MAIN');

insert into capability_permission values(559,'w');
insert into capability_permission values(560,'w');
insert into capability_permission values(561,'w');
insert into capability_permission values(562,'w');
insert into capability_permission values(563,'w');
insert into capability_permission values(564,'w');
insert into capability_permission values(565,'w');
insert into capability_permission values(566,'w');
insert into capability_permission values(567,'w');
insert into capability_permission values(568,'w');
insert into capability_permission values(569,'w');



--Segnalazione Guido Rosato avvenuta via mail il 27/11/2014
--Associazione organi tipi_esami esiti mancanti in ufficiale
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,1,75);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,2,75);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,4,75);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,5,75);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,6,75);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,1,76);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,2,76);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,4,76);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,5,76);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,6,76);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,1,77);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,2,77);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,4,77);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,5,77);
insert into lookup_autopsia_organi_tipi_esami_esiti(enabled,level,esito,tipo_esame,organo) values(true,1480,null,6,77);



update accettazione acc 
set entered_by = (select entered_by from cartella_clinica cc where cc.numero = substring(acc.richiedente_altro from (position('Cc: ' in acc.richiedente_altro) + 4 )) limit 1)  
where acc.richiedente_altro ilike 'Clinica: %';

ALTER TABLE clinica ADD COLUMN canile_bdu integer;
update clinica set canile_bdu = 4346331 where id = 65;
update clinica set canile_bdu = 4346383 where id = 4;
update clinica set canile_bdu = 4346330 where id = 10;
update clinica set canile_bdu = 4346340 where id = 11;
update clinica set canile_bdu = 4361841 where id = 61;
update clinica set canile_bdu = 4346347 where id = 9;
update clinica set canile_bdu = 4367012 where id = 13;
update clinica set canile_bdu = 4346385 where id = 5;
update clinica set canile_bdu = 4346381 where id = 78;
update clinica set canile_bdu = 4346324 where id = 75;
update clinica set canile_bdu = 4346328 where id = 80;
update clinica set canile_bdu = 4346339 where id = 81;
update clinica set canile_bdu = 4372743 where id = 3;
update clinica set canile_bdu = 4363803 where id = 33;
update clinica set canile_bdu = 4372703 where id = 34;
update clinica set canile_bdu = 4372704 where id = 40;
update clinica set canile_bdu = 4372708 where id = 42;
update clinica set canile_bdu = 4372713 where id = 32;
update clinica set canile_bdu = 4372716 where id = 41;
update clinica set canile_bdu = 4372720 where id = 39;
update clinica set canile_bdu = 4372734 where id = 36;
update clinica set canile_bdu = 4372735 where id = 35;
update clinica set canile_bdu = 4372740 where id = 38;
update clinica set canile_bdu = 4372741 where id = 37;
update clinica set canile_bdu = 4372745 where id = 63;
update clinica set canile_bdu = 4372736 where id = 62;
update clinica set canile_bdu = 4372709 where id = 28;
update clinica set canile_bdu = 4372747 where id = 29;
update clinica set canile_bdu = 4346329 where id = 1;
update clinica set canile_bdu = 4372745 where id = 31;
update clinica set canile_bdu = 4346385 where id = 43;
update clinica set canile_bdu = 4346328 where id = 6;


insert into lookup_operazioni_accettazione(
            id, alta_specialita_chirurgica, approfondimenti, approfondimento_diagnostico_medicina, 
            canina, description,       diagnostica_strumentale, effettuabile_da_morto, 
            effettuabile_fuori_asl, enabled, felina, inbdr, level, richiesta_prelievi_malattie_infettive, 
            scelta_asl, sinantropi, effettuabile_fuori_asl_morto, effettuabile_da_vivo, 
            enabled_default, obbligo_cc, id_bdr, enabled_in_page, intra_fuori_asl) 
     values(61,  FALSE,                      FALSE,           FALSE,
            TRUE,'Prelievo Leishmania',FALSE,                    TRUE,
            TRUE,                     TRUE,  true,    true,  225,                               TRUE,
            FALSE,       FALSE,         TRUE,                     true,
            false,           false,      54,        true,           false);
            
            
            
            
            

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionigatto(microchip_to_get text, asl_utente integer)
  RETURNS public_functions.lista_registrazioni_gatto AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni_gatto;
tipologia_registrazione integer;
data_vaccinazione_antirabbia timestamp without time zone;
--numero_giorni_da_vaccinazione integer;
passaporto_corrente character varying;
animale_fuori_dominio boolean;
asl_animale integer;
asl_in_carico integer;
query_da_eseguire character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false into  record_to_return;

select a.flag_ultima_operazione_eseguita_fuori_dominio_asl, a.id_asl_riferimento, a.id_asl_fuori_dominio_ultima_registrazione
into animale_fuori_dominio, asl_animale, asl_in_carico
from animale a
where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and
      trashed_date is null;



      IF (animale_fuori_dominio=false) THEN
query_da_eseguire := 'select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join gatto g on (a.id = g.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2)';
END IF;

IF (asl_utente=asl_in_carico) THEN
query_da_eseguire := 'Select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto from registrazioni_wk wk inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join gatto g on (a.id = g.id_animale)
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2) and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true and only_hd = false ';
END IF;

IF (asl_utente=asl_animale) THEN
query_da_eseguire := 'Select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from registrazioni_wk wk inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2) and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true  
and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true and only_hd = false ';

END IF;

IF (asl_utente!=asl_animale and asl_utente!=asl_in_carico) THEN
--da gestire il caso di utenti non di asl di apparteneza animale e non di asl in carico
query_da_eseguire := '';

END IF;

open cursore for execute query_da_eseguire;

LOOP

fetch cursore into tipologia_registrazione, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND; 


/*Calcolo giorni da ultima vaccinazione antirabbia
select (to_date(to_char(current_timestamp, 'YYYY/MM/DD'),  'YYYY/MM/DD') - 
to_date( to_char( data_vaccinazione_antirabbia, 'YYYY/MM/DD'), 'YYYY/MM/DD')) into  numero_giorni_da_vaccinazione;*/


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione


WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;



WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;


WHEN 19 THEN /*adozione*/
record_to_return.adozione := true;

WHEN 48 THEN /*adozione*/
record_to_return.rinnovo_passaporto := true;

WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;

WHEN 45 THEN /*restituzione a proprietario*/
record_to_return.ritornoProprietario := true;

WHEN 6 THEN /*passaporto*/
if /*((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/
(passaporto_corrente is null or passaporto_corrente = '') then
record_to_return.passaporto := true;
end if;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 24 THEN /*ricattura*/
record_to_return.ricattura := true;

WHEN 46 THEN /*adozione fuori asl / fuori regione*/
record_to_return.adozionefa := true;

WHEN 43 THEN /*modifica residenza proprietario o detentore*/
record_to_return.trasfresidenzaprop := true;

WHEN 7 THEN /*Cessione*/
record_to_return.cessione := true;

WHEN 8 THEN /*trasferimento fuori regione*/
record_to_return.trasfregione := true;

WHEN 54 THEN /*prelievo leishmania*/
record_to_return.prelievoleishmania := true;

WHEN 55 THEN /*Ritorno ad Asl di Origine*/
record_to_return.ritornoAslOrigine := true;

ELSE 

END CASE;

/* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionigatto(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text,integer) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text,integer) TO postgres;


-- Function: public_functions.get_id_registrazione(character varying, integer, timestamp without time zone)

-- DROP FUNCTION public_functions.get_id_registrazione(character varying, integer, timestamp without time zone);


CREATE OR REPLACE FUNCTION public_functions.get_id_registrazione(microchip_to_get character varying, id_tipologia_registrazione integer, data_reg timestamp without time zone)
  RETURNS integer AS
$BODY$
DECLARE  
 rec int;  
BEGIN 
SELECT reg.id_evento
 into rec
 FROM animale an
   left join evento reg ON reg.id_animale = an.id
   LEFT JOIN evento_adozione_da_canile ado_canile ON ado_canile.id_evento = reg.id_evento
   LEFT JOIN evento_adozione_da_colonia ado_colonia ON ado_colonia.id_evento = reg.id_evento
   LEFT JOIN evento_adozione_distanza ado_distanza ON ado_distanza.id_evento = reg.id_evento
   LEFT JOIN evento_cambio_detentore cambio ON cambio.id_evento = reg.id_evento
   LEFT JOIN evento_cattura cattura ON cattura.id_evento = reg.id_evento
   LEFT JOIN evento_cessione cessione ON cessione.id_evento = reg.id_evento
   LEFT JOIN evento_decesso decesso ON decesso.id_evento = reg.id_evento
   LEFT JOIN evento_esito_controlli esito ON esito.id_evento = reg.id_evento
   LEFT JOIN evento_restituzione_a_proprietario restituzione_prop ON restituzione_prop.id_evento = reg.id_evento
   LEFT JOIN evento_furto furto ON furto.id_evento = reg.id_evento
   LEFT JOIN evento_inserimento_esiti_controlli_commerciali controlli ON controlli.id_evento = reg.id_evento
   LEFT JOIN evento_inserimento_microchip ins_microchip ON ins_microchip.id_evento = reg.id_evento
   LEFT JOIN evento_inserimento_vaccino ins_vaccino ON ins_vaccino.id_evento = reg.id_evento
   LEFT JOIN evento_modifica_residenza residenza ON residenza.id_evento = reg.id_evento
   LEFT JOIN evento_morsicatura morsicatura ON morsicatura.id_evento = reg.id_evento
   LEFT JOIN evento_presa_in_carico_cessione presa ON presa.id_evento = reg.id_evento
   LEFT JOIN evento_registrazione_bdu registrazione ON registrazione.id_evento = reg.id_evento
   LEFT JOIN evento_reimmissione reimmissione ON reimmissione.id_evento = reg.id_evento
   LEFT JOIN evento_restituzione_a_canile rest_canile ON rest_canile.id_evento = reg.id_evento
   LEFT JOIN evento_rientro_da_fuori_regione rientro_fr ON rientro_fr.id_evento = reg.id_evento
   LEFT JOIN evento_rientro_da_fuori_stato rientro_fs ON rientro_fs.id_evento = reg.id_evento
   LEFT JOIN evento_rilascio_passaporto ril_passaporto ON ril_passaporto.id_evento = reg.id_evento
   LEFT JOIN evento_rilascio_passaporto rinn_passaporto ON rinn_passaporto.id_evento = reg.id_evento and rinn_passaporto.flag_rinnovo
   LEFT JOIN evento_ritrovamento ritro ON ritro.id_evento = reg.id_evento
   LEFT JOIN evento_ritrovamento_non_denunciato ritro_non_den ON ritro_non_den.id_evento = reg.id_evento
   LEFT JOIN evento_smarrimento smar ON smar.id_evento = reg.id_evento
   LEFT JOIN evento_sterilizzazione ster ON ster.id_evento = reg.id_evento
   LEFT JOIN evento_trasferimento trasf ON trasf.id_evento = reg.id_evento
   LEFT JOIN evento_trasferimento_canile trasf_canile ON trasf_canile.id_evento = reg.id_evento
   LEFT JOIN evento_trasferimento_fuori_regione trasf_fr ON trasf_fr.id_evento = reg.id_evento
   LEFT JOIN evento_trasferimento_fuori_regione_solo_proprietario trasf_fr_sp ON trasf_fr_sp.id_evento = reg.id_evento
   LEFT JOIN evento_trasferimento_fuori_stato trasf_fs ON trasf_fs.id_evento = reg.id_evento
   LEFT JOIN evento_prelievo_dna dna ON dna.id_evento = reg.id_evento
   LEFT JOIN evento_prelievo_leish leis ON leis.id_evento = reg.id_evento
   LEFT JOIN evento_restituzione_asl_origine rit_asl_orig ON rit_asl_orig.id_evento = reg.id_evento
  WHERE 
       reg.microchip = microchip_to_get and 
       reg.id_tipologia_evento = id_tipologia_registrazione and
       reg.data_cancellazione IS NULL and 
       reg.trashed_date is null AND 
       ((reg.id_tipologia_evento = 1 and registrazione.data_registrazione = data_reg)
	or(reg.id_tipologia_evento = 2 and ster.data_sterilizzazione = data_reg)
	or(reg.id_tipologia_evento = 3 and ins_microchip.data_inserimento_microchip = data_reg)
	or(reg.id_tipologia_evento = 4 and furto.data_furto = data_reg)
	or(reg.id_tipologia_evento = 5 and cattura.data_cattura = data_reg)
	or(reg.id_tipologia_evento = 6 and ril_passaporto.data_rilascio_passaporto = data_reg)
	or(reg.id_tipologia_evento = 7 and cessione.data_cessione = data_reg)
	or(reg.id_tipologia_evento = 8 and trasf_fr.data_trasferimento_fuori_regione = data_reg)
	or(reg.id_tipologia_evento = 9 and decesso.data_decesso = data_reg)
	or(reg.id_tipologia_evento = 11 and smar.data_smarrimento = data_reg)
	or(reg.id_tipologia_evento = 12 and ritro.data_ritrovamento = data_reg)
	or(reg.id_tipologia_evento = 41 and ritro_non_den.data_ritrovamento_nd = data_reg)
        or(reg.id_tipologia_evento = 13 and ado_canile.data_adozione = data_reg)
        or(reg.id_tipologia_evento = 14 and rest_canile.data_restituzione_canile = data_reg)
        or(reg.id_tipologia_evento = 15 and presa.data_presa_in_carico = data_reg)
        or(reg.id_tipologia_evento = 16 and trasf.data_trasferimento = data_reg)
        or(reg.id_tipologia_evento = 17 and rientro_fr.data_rientro_fr = data_reg)
        or(reg.id_tipologia_evento = 18 and cambio.data_cambio_detentore = data_reg)
        or(reg.id_tipologia_evento = 19 and ado_colonia.data_adozione_colonia = data_reg)
        or(reg.id_tipologia_evento = 21 and morsicatura.data_morso = data_reg)
        or(reg.id_tipologia_evento = 23 and reimmissione.data_reimmissione = data_reg)
        or(reg.id_tipologia_evento = 45 and restituzione_prop.data_restituzione = data_reg)
        or(reg.id_tipologia_evento = 24 and cattura.data_cattura = data_reg)
        or(reg.id_tipologia_evento = 31 and trasf_canile.data_trasferimento_canile = data_reg)
        or(reg.id_tipologia_evento = 36 and ins_vaccino.data_inserimento_vaccinazione = data_reg)
        or(reg.id_tipologia_evento = 38 and ins_microchip.data_inserimento_microchip = data_reg)
        or(reg.id_tipologia_evento = 39 and trasf_fs.data_trasferimento_fuori_stato = data_reg)
        or(reg.id_tipologia_evento = 40 and trasf_fr_sp.data_trasferimento_fuori_regione_solo_proprietario = data_reg)
        or(reg.id_tipologia_evento = 42 and rientro_fs.data_rientro_fuori_stato = data_reg)
        or(reg.id_tipologia_evento = 43 and residenza.data_modifica_residenza = data_reg)
        or(reg.id_tipologia_evento = 48 and rinn_passaporto.data_rilascio_passaporto = data_reg)
        or(reg.id_tipologia_evento = 50 and dna.data_prelievo = data_reg)
        or(reg.id_tipologia_evento = 55 and rit_asl_orig.data_restituzione_asl = data_reg)
         or(reg.id_tipologia_evento = 54 and leis.data_prelievo_leish = data_reg));
     RETURN rec;
 END;
$BODY$
  LANGUAGE plpgsql VOLATILE STRICT
  COST 100;
ALTER FUNCTION public_functions.get_id_registrazione(character varying, integer, timestamp without time zone)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.get_id_registrazione(character varying, integer, timestamp without time zone) TO public;
GRANT EXECUTE ON FUNCTION public_functions.get_id_registrazione(character varying, integer, timestamp without time zone) TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.get_id_registrazione(character varying, integer, timestamp without time zone) TO guc;


-- Function: public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer)

-- DROP FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer);

CREATE OR REPLACE FUNCTION public_functions.inseriscidecesso(microchip_to_get character varying, data timestamp without time zone, idcausadecesso integer, flagdatapresunta boolean, codiceistatcomunedecesso character varying, indirizzodecesso character varying, note character varying, user_username character varying, caller integer)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idProprietario integer;
 idDetentore integer;
 idProssimoStato integer;
 cursore refcursor;
 cursore1 refcursor;
 cursore2 refcursor;
 idEvento integer;

 /*utente*/
 idUtente integer;
 idAslUtente integer;


 /*indirizzo*/
 idProvincia integer;
 idComune integer;

 
 BEGIN

 /*Dati animale*/
 open cursore for select id, id_asl_riferimento, id_specie, stato, id_proprietario, id_detentore from animale where microchip = microchip_to_get and trashed_date is null; 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato, idProprietario, idDetentore;
EXIT WHEN NOT FOUND; 


/*dati utente*/
 
open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 

/*dati indirizzo decesso*/

raise info '%', codiceistatcomunedecesso is not null;

if(codiceistatcomunedecesso is not null) then
  select id, cod_provincia from comuni1 where istat = codiceistatcomunedecesso into idComune, idProvincia;
end if;

raise info 'daticomune % % ', idComune, idProvincia;
raise info 'datiutente % % ', idUtente, idAslUtente;

/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale, id_stato_originale, note, id_proprietario_corrente, id_detentore_corrente,  origine_registrazione,
	inserimento_registrazione_forzato)
    VALUES (idAnimale, microchip_to_get, now(), now(), idUtente, idUtente, 
            9, idAslUtente, idSpecie, idStato, note, idProprietario, idDetentore, caller, false) RETURNING id_evento INTO idEvento;   

INSERT INTO evento_decesso(
            id_evento, data_decesso, flag_decesso_presunto, id_causa_decesso,   id_provincia_decesso,  id_comune_decesso, indirizzo_decesso)
    VALUES ( idEvento, data,  
            flagDataPresunta, idCausaDecesso, idProvincia, idComune, indirizzodecesso);





Select id_prossimo_stato into idProssimoStato from registrazioni_wk where id_stato = idStato and id_registrazione = 9;

Update animale set stato = idProssimoStato, flag_decesso=true where microchip = microchip_to_get;



 
  END LOOP;
  END LOOP;


return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer) TO public;
GRANT EXECUTE ON FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer) TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.inseriscidecesso(character varying, timestamp without time zone, integer, boolean, character varying, character varying, character varying, character varying, integer) TO guc;



-- DROP FUNCTION public_functions.getlistaregistrazionicane(text, integer);

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionicane(microchip_to_get text, asl_utente integer)
  RETURNS lista_registrazioni AS
$BODY$
DECLARE  
cursore	refcursor;
cursore2 refcursor;
record_to_return public_functions.lista_registrazioni;
tipologia_registrazione integer;
flag_prelievo boolean;
data_vaccinazione_antirabbia timestamp without time zone;
/*numero_giorni_da_vaccinazione integer;*/
passaporto_corrente character varying;
animale_fuori_dominio boolean;
asl_animale integer;
asl_in_carico integer;
query_da_eseguire character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false, false into  record_to_return;

select a.flag_ultima_operazione_eseguita_fuori_dominio_asl, a.id_asl_riferimento, a.id_asl_fuori_dominio_ultima_registrazione
into animale_fuori_dominio, asl_animale, asl_in_carico
from animale a
where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and
      trashed_date is null;

IF (animale_fuori_dominio=false) THEN
query_da_eseguire := 'select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join cane c on (a.id = c.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1)';
END IF;

IF (asl_utente=asl_in_carico) THEN
query_da_eseguire := 'Select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto from registrazioni_wk wk inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join cane c on (a.id = c.id_animale)
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1) and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true and only_hd = false ';
END IF;

IF (asl_utente=asl_animale) THEN
query_da_eseguire := 'Select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from registrazioni_wk wk inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1) and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true  
and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true and only_hd = false ';

END IF;

IF (asl_utente<>asl_animale and asl_utente<>asl_in_carico) THEN
--da gestire il caso di utenti non di asl di apparteneza animale e non di asl in carico
query_da_eseguire := '';

END IF;

open cursore for execute query_da_eseguire;


LOOP

fetch cursore into tipologia_registrazione, flag_prelievo, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND;


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione

WHEN 24 then /* RICATTURA*/
record_to_return.ricattura := true;

WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;

WHEN 48 then /*FURTO*/
raise info 'entrato nel case when 48 val %', tipologia_registrazione;
record_to_return.rinnovo_passaporto := true;

WHEN 6 then /*PASSAPORTO*/
raise info 'entrato nel case when 6 val %', tipologia_registrazione;
/*if ((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/ 
if (passaporto_corrente is null or passaporto_corrente = '')
then
record_to_return.passaporto := true;
end if;

WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;


WHEN 7 then /*Cessione*/
record_to_return.cessione := true;
raise info 'entrato nel case when 7 val %', tipologia_registrazione;

WHEN 26 THEN /*Controlli commerciali*/
record_to_return.controllComm := true;

WHEN 22 THEN /*controlli*/
record_to_return.controlli := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;

WHEN 15 THEN /*presa in carico da cessione*/
record_to_return.gestCessione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;

WHEN 45 THEN /*restituzione a proprietario*/
record_to_return.ritornoProprietario := true;

WHEN 8 THEN /*trasferimento fr*/
record_to_return.trasfRegione := true;

WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;

WHEN 17 THEN /*trasferimento*/
record_to_return.rientro := true;


WHEN 13 THEN
record_to_return.adozione := true;


WHEN 41 THEN  /* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

WHEN 50 THEN /* PRELIEVO DNA*/
if (flag_prelievo = false or flag_prelievo is NULL) then
record_to_return.prelievodna := true;
end if;

WHEN 46 THEN /* ADOZIONE FUORI ASL, FUORI REGIONE*/
record_to_return.adozionefa := true;

WHEN 43 THEN /* MODIFICA RESIDENZA PROPRIETARIO O DETENTORE*/
record_to_return.trasfresidenzaprop := true;

WHEN 54 THEN /*Prelievo Leishmania*/
record_to_return.prelievoleishmania := true;

WHEN 55 THEN /*Ritorno ad Asl di Origine*/
record_to_return.ritornoAslOrigine := true;

ELSE

END CASE;
END LOOP;

return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionicane(text, integer)
  OWNER TO postgres;
   
  
-- checkUtenti
ALTER TABLE vam_storico_operazioni_utenti ADD COLUMN action text;

--05/02/2015 - Stefano Squitieri - Ricattura automatica
CREATE OR REPLACE FUNCTION public_functions.inserisciricattura(microchip_to_get character varying, data timestamp without time zone, user_username character varying, idDetentoreSuccessivo integer, caller integer)
  RETURNS integer AS
$BODY$
DECLARE  
 idRegistrazione integer;
 idAnimale integer;
 idAsl integer;
 idSpecie integer;
 idStato integer;
 idEvento integer;
 idProprietario integer;
 idDetentore integer;
 idProssimoStato integer;
 idComuneCattura integer;
 cursore refcursor;
 cursore1 refcursor;
 cursore2 refcursor;
 
 /*utente*/
 idUtente integer;
 idAslUtente integer;

 
 BEGIN

 open cursore for select id, id_asl_riferimento, id_specie, stato, id_proprietario, id_detentore from animale where (microchip = microchip_to_get or tatuaggio = microchip_to_get); 
 LOOP
fetch cursore into idAnimale, idAsl, idSpecie, idStato,idProprietario,idDetentore;
EXIT WHEN NOT FOUND; 


open cursore1 for select user_id, site_id  from access where username = user_username and enabled = true; /*Recupero idUtente da username*/ 
LOOP
fetch cursore1 into idUtente, idAslUtente;
EXIT WHEN NOT FOUND; 

raise info 'datiutente % % ', idUtente, idAslUtente;


open cursore2 for select comune from opu_operatori_denormalizzati where id_rel_stab_lp = idProprietario; 
LOOP
fetch cursore2 into idComuneCattura;
EXIT WHEN NOT FOUND; 

raise info 'idComuneCattura % ', idComuneCattura;


/*savepoint my_save;*/
raise info 'dati % % %', idAnimale, idAsl, idSpecie;
INSERT INTO evento(
            id_animale, microchip, entered, modified, id_utente_inserimento, 
            id_utente_modifica, id_tipologia_evento, id_asl,  
            id_specie_animale, id_stato_originale, note, id_proprietario_corrente, id_detentore_corrente, 
            origine_registrazione, inserimento_registrazione_forzato)
    VALUES (idAnimale, microchip_to_get, now(), now(), idUtente, idUtente, 
            24, idAslUtente, idSpecie, idStato, '', idProprietario, idDetentore, caller, false) RETURNING id_evento INTO idEvento;   

INSERT INTO evento_cattura(
            id_evento, data_cattura, id_comune_cattura,verbale_cattura,luogo_cattura,flag_ricattura, id_detentore_cattura,id_proprietario_cattura)
    VALUES ( idEvento,         data,   idComuneCattura,             '',           '',          true,idDetentoreSuccessivo,         idProprietario);



Select id_prossimo_stato into idProssimoStato from registrazioni_wk where id_stato = idStato and id_registrazione = 24;

Update animale set stato = idProssimoStato, id_detentore = idDetentoreSuccessivo where (microchip = microchip_to_get or tatuaggio = microchip_to_get);



 END LOOP;
  END LOOP;
  END LOOP;


return idEvento;

/*EXCEPTION
  WHEN OTHERS THEN
 raise notice 'The transaction is in an uncommittable state. '
                 'Transaction was rolled back';
                  ROLLBACK to my_save;

   raise notice '% %', SQLERRM, SQLSTATE;
   ROLLBACK to my_save;
    RAISE;
*/
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.inserisciricattura(character varying, timestamp without time zone, character varying, integer)
  OWNER TO postgres;
  
  
CREATE TYPE public_functions.esito_leishmaniosi AS
   (identificativo text,
    data_prelievo_leishmaniosi timestamp without time zone,
    data_accertamento timestamp without time zone,
    data_esito_leishmaniosi timestamp without time zone,
    esito text,
    esito_car text);
ALTER TYPE public_functions.esito_leishmaniosi
  OWNER TO postgres;

  
  
  
CREATE OR REPLACE FUNCTION public_functions.getesitileishmaniosi(microchip text, data timestamp without time zone)
  RETURNS SETOF public_functions.esito_leishmaniosi AS
$BODY$select identificativo,
             data_prelievo as data_prelievo_leishmaniosi,
             data_accertamento,
             data_esito as data_esito_leishmaniosi,
             esito,
             esito_car
      from esiti_leishmaniosi e
     where e.identificativo = $1 and ($2 is null or e.data_esito <= $2)$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
ALTER FUNCTION public_functions.getesitileishmaniosi(text, timestamp without time zone)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getesitileishmaniosi(text, timestamp without time zone) TO public;
GRANT EXECUTE ON FUNCTION public_functions.getesitileishmaniosi(text, timestamp without time zone) TO postgres;



--26/03/2015, Prelievo Leishmania in CC
CREATE OR REPLACE FUNCTION public_functions.getregprelcampionileish(IN id_reg integer)
  RETURNS table(id_evento integer, dataPrelievoLeishmaniosi timestamp without time zone,veterinario text) AS
$BODY$
BEGIN
FOR id_evento,dataPrelievoLeishmaniosi,veterinario IN 
  select e.id_evento,
         data_prelievo_leish,
         cont.namefirst || ' ' || cont.namelast as veterinario
  from evento e, animale a , evento_prelievo_leish e_p, access acc, contact cont
  where a.data_cancellazione is null and a.trashed_date is null and
  e.id_animale = a.id and (acc.user_id = e_p.id_veterinario_llpp or acc.user_id = e_p.id_veterinario_asl) and 
  e.trashed_date is null and e.data_cancellazione is null and id_tipologia_evento = 54 and e_p.id_evento = e.id_evento and e.id_evento=id_reg and cont.contact_id = acc.contact_id
  LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.get_id_registrazioni_leish(integer)
  OWNER TO postgres;


CREATE OR REPLACE FUNCTION public_functions.get_id_registrazioni_leish(IN microchip_to_get text, IN data_start timestamp without time zone,IN data_end timestamp without time zone)
  RETURNS table(id_evento integer) AS
$BODY$
BEGIN
FOR id_evento IN 
  select e.id_evento
  from evento e, animale a , evento_prelievo_leish e_p
  where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.data_cancellazione is null and a.trashed_date is null and
  e.id_animale = a.id and
  e.trashed_date is null and e.data_cancellazione is null and id_tipologia_evento = 54 and e_p.id_evento = e.id_evento and e_p.data_prelievo_leish>= data_start and 
  (data_end is null or data_prelievo_leish<=data_end)
  LOOP
        RETURN NEXT;
     END LOOP;
     RETURN;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.get_id_registrazioni_leish( text, timestamp without time zone,timestamp without time zone)
  OWNER TO postgres;
  

ALTER TABLE leishmaniosi ADD COLUMN veterinario text;


tipo_controlli su evento_html_fields



--27/03/2015 - Inserimento nuovo proprietario da adozione



  INSERT INTO evento_html_fields(
            id,id_tipologia_evento, nome_campo, tipo_campo, label_campo, 
            tabella_lookup, javascript_function, javascript_function_evento, 
             only_hd, 
            label_link)
    VALUES (331,13, 'nuovoProprietario', 'link', 'Inserisci nuovo proprietario', 
            null, 'popUp(''OperatoreAction.do?command=Add&popup=true&tipologiaSoggetto=1'')', null, 
             0, 
            'Inserisci nuovo proprietario');

            
  INSERT INTO evento_html_fields(
            id,id_tipologia_evento, nome_campo, tipo_campo, label_campo, 
            tabella_lookup, javascript_function, javascript_function_evento, 
            only_hd, 
            label_link)
    VALUES (332,19, 'nuovoProprietario', 'link', 'Inserisci nuovo proprietario', 
            null, 'popUp(''OperatoreAction.do?command=Add&popup=true&tipologiaSoggetto=1'')', null, 
             0, 
            'Inserisci nuovo proprietario');


            
            
            -- Sinantropi/Marini/Zoo
INSERT INTO lookup_detentori_sinantropi
(description, email, enabled, fax,      indirizzo, level, telefono,  comune,zoo)
VALUES 
('Zoo di Napoli',           null,    true,   null,          'Via John Fitzgerald Kennedy, 76, 80125 Napoli',    10,        '08119363154',      313,true);

update sinantropo set sinantropo = true, zoo = false, marini = false;

ALTER TABLE lookup_specie_sinantropi ADD COLUMN uccello_z boolean;
ALTER TABLE lookup_specie_sinantropi ADD COLUMN rettile_anfibio_z boolean;
ALTER TABLE lookup_specie_sinantropi ADD COLUMN mammifero_z boolean;

update lookup_specie_sinantropi set uccello_z = false, rettile_anfibio_z = false, mammifero_z = false;

INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Antilope Bongo', true, 2370, false, false, 
            false, false, false, false, false, true, false);

            
INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Babbuino', true, 2380, false, false, 
            false, false, false, false, false, true, false);

 INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Bue highlands', true, 2390, false, false, 
            false, false, false, false, false, true, false);
            
INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Bue watussi', true, 2400, false, false, 
            false, false, false, false, false, true, false);
            
 INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Cammello', true, 2410, false, false, 
            false, false, false, false, false, true, false);

 INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Cavallo', true, 2420, false, false, 
            false, false, false, false, false, true, false);
            
INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Delfino', true, 2430, false, false, 
            false, false, false, false, false, true, false);

 INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Dromedario', true, 2440, false, false, 
            false, false, false, false, false, true, false);

INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Foca', true, 2450, false, false, 
            false, false, false, false, false, true, false);
            
             INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Gibbone', true, 2460, false, false, 
            false, false, false, false, false, true, false);

            INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Gorilla', true, 2470, false, false, 
            false, false, false, false, false, true, false);
            
INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Ippopotamo', true, 2480, false, false, 
            false, false, false, false, false, true, false);
                        
            INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Leopardo', true, 2525, false, false, 
            false, false, false, false, false, true, false);
            
INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Lama', true, 2500, false, false, 
            false, false, false, false, false, true, false);

 INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Lemure', true, 2510, false, false, 
            false, false, false, false, false, true, false);

 INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Leone marino', true, 2520, false, false, 
            false, false, false, false, false, true, false);
            
            INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Licaone', true, 2530, false, false, 
            false, false, false, false, false, true, false);
            
INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Orso bruno', true, 2540, false, false, 
            false, false, false, false, false, true, false);

             INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Orso polare', true, 2550, false, false, 
            false, false, false, false, false, true, false);

            INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Otaria', true, 2560, false, false, 
            false, false, false, false, false, true, false);
            
            INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Rinoceronte', true, 2570, false, false, 
            false, false, false, false, false, true, false);

  INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Scimpanze', true, 2580, false, false, 
            false, false, false, false, false, true, false);
            
  INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Yak', true, 2590, false, false, 
            false, false, false, false, false, true, false);          

INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Zebra di Grant', true, 2600, false, false, 
            false, false, false, false, false, true, false);

INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Cicogna', true, 2300, false, false, 
            false, false, false, false, true, false, false);

INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Cigno', true, 2310, false, false, 
            false, false, false, false, true, false, false);
            
INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Fenicottero', true, 2320, false, false, 
            false, false, false, false, true,  false, false);

            INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Oca', true, 2330, false, false, 
            false, false, false, false, true, false, false);
            
INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Pellicano', true, 2340, false, false, 
            false, false, false, false, true, false, false);
            
            INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Pinguino', true, 2350, false, false, 
            false, false, false, false, true, false, false);

            INSERT INTO lookup_specie_sinantropi(
            description, enabled, level, mammifero, rettile_anfibio, 
            uccello, selaci, mammifero_cetaceo, rettile_testuggine, uccello_z, mammifero_z, rettile_anfibio_z)
    VALUES ('Struzzo', true, 2360, false, false, 
            false, false, false, false, true, false, false);
            
            
            
-- Modifiche del 27/10/2015
update lookup_operazioni_accettazione set description = 'Trasporto Spoglie (Attivita'' B6)' where description ilike '%carcassa%';
update lookup_accettazione_attivita_esterna  set description = 'Pronto Soccorso (Attivita'' C4)' where description ilike '%pronto soccorso%';
update lookup_accettazione_attivita_esterna  set description = 'Identificazione,registrazione e destino delle carcasse di cani e gatti (Attivita'' B6)' where description ilike '%identificazione%carcasse%';
ALTER TABLE lookup_operazioni_accettazione ADD COLUMN hidden_in_page boolean default true;
update lookup_operazioni_accettazione set hidden_in_page = false where id = 12;
ALTER TABLE lookup_personale_interno ADD COLUMN hidden_in_page boolean default true;
update lookup_personale_interno set hidden_in_page = false where nominativo ilike '%de filippo%';
update lookup_personale_interno set hidden_in_page = false where nominativo ilike '%lendano ciro%';
update lookup_personale_interno set hidden_in_page = false where nominativo ilike '%vitale agostino%';
insert into lookup_personale_interno(id, enabled, nominativo, asl) values (45, true,'Ferraro Fabrizio', 204);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (9,TRUE,'disable',53,48);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (10,TRUE,'disable',53,23);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (11,TRUE,'disable',53,22);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (12,TRUE,'disable',53,21);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (13,TRUE,'disable',53,20);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (14,TRUE,'disable',53,19);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (15,TRUE,'disable',53,18);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (16,TRUE,'disable',53,17);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (17,TRUE,'disable',53,16);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (18,TRUE,'disable',53,15);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (19,TRUE,'disable',53,11);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (20,TRUE,'disable',53,14);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (21,TRUE,'disable',53,43);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (22,TRUE,'disable',53,44);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (23,TRUE,'disable',53,45);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (24,TRUE,'disable',53,42);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (25,TRUE,'disable',53,41);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (26,TRUE,'disable',53,40);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (27,TRUE,'disable',53,39);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (28,TRUE,'disable',53,38);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (29,TRUE,'disable',53,37);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (30,TRUE,'disable',53,36);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (31,TRUE,'disable',53,35);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (32,TRUE,'disable',53,34);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (33,TRUE,'disable',53,33);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (34,TRUE,'disable',53,32);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (35,TRUE,'disable',53,31);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (36,TRUE,'disable',53,30);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (37,TRUE,'disable',53,29);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (38,TRUE,'disable',53,28);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (39,TRUE,'disable',53,12);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (40,TRUE,'disable',53,47);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (41,TRUE,'disable',53,8);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (42,TRUE,'disable',53,27);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (43,TRUE,'disable',53,26);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (44,TRUE,'disable',53,25);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (46,TRUE,'disable',53,4);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (47,TRUE,'disable',53,54);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (48,TRUE,'disable',53,56);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (49,TRUE,'disable',53,50);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (50,TRUE,'disable',53,5);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (51,TRUE,'disable',53,61);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (52,TRUE,'disable',53,62);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (53,TRUE,'disable',53,2);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (54,TRUE,'disable',53,46);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (55,TRUE,'disable',53,52);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (56,TRUE,'disable',53,63);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (57,TRUE,'disable',53,49);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (58,TRUE,'disable',53,51);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (59,TRUE,'disable',53,3);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (60,TRUE,'disable',53,57);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (61,TRUE,'disable',53,9);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (62,TRUE,'disable',53,7);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (63,TRUE,'disable',53,58);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (64,TRUE,'disable',53,6);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (65,TRUE,'disable',53,59);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (66,TRUE,'disable',53,60);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (67,TRUE,'disable',53,64);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (68,TRUE,'disable',53,24);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (69,TRUE,'disable',53,10);
insert into lookup_operazioni_accettazione_condizionate(id,enabled, operazione_da_fare, operazione_condizionata, operazione_condizionante) values (70,TRUE,'disable',53,13);

--Modifiche del 29/09 
alter table lookup_autopsia_patologie_prevalenti add column esclusivo  boolean default true;
update lookup_autopsia_patologie_prevalenti set esclusivo = false where id in (121, 122, 123);


--Modifiche verbale 22/12/2015
ALTER TABLE lookup_accettazione_attivita_esterna ADD COLUMN effettuabile_da_morto boolean;
update lookup_accettazione_attivita_esterna set effettuabile_da_morto = false where id = 2;
update lookup_accettazione_attivita_esterna set effettuabile_da_morto = true where id = 1;

--Modifiche verbale del 01/03
update lookup_taglie set description = 'Piccola: fino a Kg 2'  where id = 1;
update lookup_taglie set description = 'Media: da Kg 2 a 8'  where id = 2;
update lookup_taglie set description = 'Grande: da Kg 8 a 15'  where id = 3;
update lookup_taglie set description = 'Gigante: sopra ai Kg 15'  where id = 5;






--Richieste Rosato 1 Marzo
update lookup_autopsia_organi set level_sde = 215, description = 'Miocardio' where description ilike '%cuore%';

INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Epicardio', true, true, 421, 85, false, 
            true, true, false, true, false);
INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Valvole', true, true, 422, 435 , false, 
            true, true, false, true, false);
INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Arterie Coronarie', true, true, 423,5, false, 
            true, true, false, true, false);

insert into organi_patologieprevalenti values(1,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(2,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(3,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(4,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(5,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(6,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(7,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(8,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(9,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(10,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(1,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(2,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(3,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(4,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(5,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(6,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(7,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(8,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(9,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(10,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(1,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(2,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(3,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(4,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(5,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(6,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(7,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(8,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(9,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(10,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(135,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(136,(select id from lookup_autopsia_organi where description ilike '%Epicardio%'));
insert into organi_patologieprevalenti values(135,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(136,(select id from lookup_autopsia_organi where description ilike '%Valvole%'));
insert into organi_patologieprevalenti values(135,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));
insert into organi_patologieprevalenti values(136,(select id from lookup_autopsia_organi where description ilike '%arterie coronarie%'));

INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Epicardio', true, true, 571, 85, false, 
            false, false, true, false, false);
INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Valvole', true, true, 572, 435 , false, 
            false, false, true, false, false);
INSERT INTO lookup_autopsia_organi(
            description, enabled, enabled_sde, level, level_sde, tessuto, 
            cani, gatti, uccelli, mammiferi, rettili)
    VALUES ('Arterie Coronarie', true, true, 573,5, false, 
            false, true, true, false, false);


insert into organi_patologieprevalenti values(55,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%' and uccelli));
insert into organi_patologieprevalenti values(56,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'and uccelli));
insert into organi_patologieprevalenti values(57,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'and uccelli));
insert into organi_patologieprevalenti values(131,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'and uccelli));
insert into organi_patologieprevalenti values(132,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'and uccelli));
insert into organi_patologieprevalenti values(135,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'and uccelli));
insert into organi_patologieprevalenti values(136,(select max(id) from lookup_autopsia_organi where description ilike '%Epicardio%'and uccelli));
insert into organi_patologieprevalenti values(55,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'and uccelli));
insert into organi_patologieprevalenti values(56,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'and uccelli));
insert into organi_patologieprevalenti values(57,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'and uccelli));
insert into organi_patologieprevalenti values(131,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'and uccelli));
insert into organi_patologieprevalenti values(132,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'and uccelli));
insert into organi_patologieprevalenti values(135,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'and uccelli));
insert into organi_patologieprevalenti values(136,(select max(id) from lookup_autopsia_organi where description ilike '%Valvole%'and uccelli));
insert into organi_patologieprevalenti values(55,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'and uccelli));
insert into organi_patologieprevalenti values(56,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'and uccelli));
insert into organi_patologieprevalenti values(57,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'and uccelli));
insert into organi_patologieprevalenti values(131,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'and uccelli));
insert into organi_patologieprevalenti values(132,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'and uccelli));
insert into organi_patologieprevalenti values(135,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'and uccelli));
insert into organi_patologieprevalenti values(136,(select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'and uccelli));
insert into organi_patologieprevalenti values(164,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(165,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(166,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(167,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(168,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(169,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(170,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(171,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(172,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(173,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(174,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(175,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(176,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(177,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(178,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(179,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(180,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(181,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
insert into organi_patologieprevalenti values(182,(select id from lookup_autopsia_organi where description ilike '%linfonodi del collo%'));
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%arterie coronarie%'), 
6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%valvole%'), 
6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select max(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
6, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
1, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
2, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
4, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
5, null, 1470);
INSERT INTO lookup_autopsia_organi_tipi_esami_esiti(id, enabled, organo, tipo_esame, esito, level) VALUES 
(nextval('lookup_autopsia_organi_tipi_esami_esiti_id_seq'), true, (select min(id) from lookup_autopsia_organi where description ilike '%epicardio%'), 
6, null, 1470);

delete from organi_patologieprevalenti where organo = 47 and patologia_prevalente in (1,2,3,4,5,6,7,8,9,10,135,136);

insert into lookup_detentori_sinantropi(description,enabled,level,zoo) values('Circo', true, 20, true);








---III Livello Registro Tumori Animale
--I livello
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre is null 
update lookup_esame_istopatologico_sede_lesione set enabled = false where id in (185, 75); 

--II livello
--140-149: Labbra Cavita Orale E Faringe
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 1
update lookup_esame_istopatologico_sede_lesione set description = 'Labbro' where id = 2; 
update lookup_esame_istopatologico_sede_lesione set description = 'Gengiva' where id = 5; 
update lookup_esame_istopatologico_sede_lesione set enabled = false where id in ( 6, 9, 10, 11); 
update lookup_esame_istopatologico_sede_lesione set description = 'Bocca:Altre Parti E Parti Non Specificate' where id = 7; 

--150-159: Apparato Digerente E Peritoneo
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 12
update lookup_esame_istopatologico_sede_lesione set enabled = false where id = 22; 


--160-165: Apparato respiratorio ed organi intratoracici
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 23;
update lookup_esame_istopatologico_sede_lesione set enabled = false where id = 29;  

--169: Apparato respiratorio ed organi intratoracici
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 30;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (186,'169', 'Sistemi Emopoietico E Reticoloendoteliale', true, '300', 30);

--170: Ossa, articolazioni e cartilagini articolari
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 31;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (187,'170', 'Ossa, articolazioni e cartilagini articolari', true, '300', 31);

--171: Tessuto connettivo, sottocutaneo e altre parti molli
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 32;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (188,'171', 'Tessuto Connettivo, Sottocutaneo E Altre Parti Molli', true, '300', 32);

--173: Cute
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 33;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (189,'173', 'Cute', true, '300', 33);

--174: Mammella Femminile
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 74;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (190,'174', 'Mammella Femminile', true, '300', 74);

--179-189: organi genitourinari 
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 76;
update lookup_esame_istopatologico_sede_lesione set enabled = false where id in( 78, 80);  
update lookup_esame_istopatologico_sede_lesione set description = 'Apparato Urogenitale Femminile:Altri Organi E Sedi Non Specificate' where id = 82;

--190: Occhio E Ghiandola Lacrimale
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 88;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (191,'90', 'Occhio E Ghiandola Lacrimale', true, '300', 88); 

--191-192: sistema Nervoso
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 89;
update lookup_esame_istopatologico_sede_lesione set enabled = false where id in(91);  

--193-194: ghiandole endocrine
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 92;

--195: Altre Sedi Mal Definite
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 95;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (192,'90', 'Altre Sedi Mal Definite', true, '300', 95); 

--196: Linfonodi
select * from lookup_esame_istopatologico_sede_lesione where enabled and padre = 96;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (193,'90', 'Linfonodi', true, '300', 96);
update lookup_esame_istopatologico_sede_lesione set enabled = false where id in(97, 116,131, 155, 165, 174, 175, 176, 177, 178, 179, 180, 181, 182, 183, 184);


--III livello
--140: Labbro
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '140';
select * from lookup_esame_istopatologico_sede_lesione where padre = 2;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%labbro%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (194,'140.0', 'Labbro superiore', true, '300', 2);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (195,'140.1', 'Labbro inferiore', true, '300', 2);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (196,'140.9', 'Labbro, N.A.S.', true, '300', 2);

--141: Lingua
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '141';
select * from lookup_esame_istopatologico_sede_lesione where padre = 3;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%lingua%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (198,'141.9', 'Lingua N.A.S.', true, '300', 3);


--142: Ghiandole Salivari Maggiori 
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '142';
select * from lookup_esame_istopatologico_sede_lesione where padre = 4;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%Ghiandola%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (199,'142.0', 'Parotide', true, '300', 4);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (200,'142.1', 'Ghiandola sottomandibolare', true, '300', 4);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (201,'142.2', 'Ghiandola sottolinguale', true, '300', 4);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (202,'142.9', 'Ghiandola salivare, N.A.S.', true, '300', 4);


--143: Gengiva
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '143';
select * from lookup_esame_istopatologico_sede_lesione where padre = 5;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%gengiva%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (203,'143.9', 'Gengiva, N.A.S.', true, '300', 5);


--145: Bocca:Altre Parti E Parti Non Specificate 
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '145';
select * from lookup_esame_istopatologico_sede_lesione where padre = 7;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%palato%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (204,'145.5', 'Palato', true, '300', 7);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (205,'145.9', 'Bocca, N.A.S.', true, '300', 7);


--146: Orofaringe
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '146';
select * from lookup_esame_istopatologico_sede_lesione where padre = 8;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%palato%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (206,'146.0', 'Tonsilla', true, '300', 8);



--150: Esofago
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '150';
select * from lookup_esame_istopatologico_sede_lesione where padre = 12;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%esofago%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (207,'150.0', 'Esofago cervicale', true, '300', 12);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (208,'150.1', 'Esofago toracico', true, '300', 12);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (209,'150.2', 'Esofago addominale', true, '300', 12);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (210,'150.9', 'Esofago N.A.S.', true, '300', 12);



--151: Stomaco
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '151';
select * from lookup_esame_istopatologico_sede_lesione where padre = 14;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%esofago%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (211,'151.0', 'Cardias, giunzione gastroesofagea', true, '300', 14);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (212,'151.1', 'Piloro', true, '300', 14);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (213,'151.9', 'Stomaco, N.A.S.', true, '300', 14);






--152: Intestino Tenue
--padre II livello
select * from lookup_esame_istopatologico_sede_lesione where codice = '152';
select * from lookup_esame_istopatologico_sede_lesione where padre = 15;
--vecchi figli
select * from lookup_esame_istopatologico_sede_lesione where description ilike '%esofago%';

INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (214,'152.0', 'Duodeno', true, '300', 15);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (215,'152.1', 'Digiuno', true, '300', 15);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (216,'152.2', 'Ileo', true, '300', 15);



INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (218,'196.9', 'Linfonodo, N.A.S.', true, '350', 193);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (219,'196.5', 'Linfonodi della regione inguinale e dell''arto posteriore', true, '340', 193);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (220,'196.3', 'Linfonodi dell''ascella e dell''arto anteriore', true, '330', 193);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (221,'196.2', 'Linfonodi intra-addominali', true, '320', 193);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (222,'196.1', 'Linfonodi intra-toracici', true, '310', 193);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (223,'196.0', 'Linfonodi della testa e collo', true, '300', 193);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (224,'195.9', 'Sedi mal definite, N.A.S.', true, '300', 192);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (225,'194.4', 'Epifisi', true, '330', 94);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (226,'194.3', 'Ipofisi, dotto craniofaringeo', true, '320', 94);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (227,'194.1', 'Paratiroide', true, '310', 94);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (228,'194.0', 'Ghiandola surrenale', true, '300', 94);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (229,'193.0', 'Tiroide', true, '300', 30);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (230,'191.9', 'Encefalo, N.A.S.', true, '310', 90);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (231,'191.6', 'Cervelletto', true, '300', 90);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (232,'190.9', 'Occhio, N.A.S.', true, '310', 191);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (233,'190.2', 'Ghiandola lacrimale', true, '300', 191);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (234,'189.0', 'Rene', true, '300', 87);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (235,'188.9', 'Vescica, N.A.S.', true, '300', 86);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (236,'187.4', 'Pene', true, '310', 85);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (237,'187.1', 'Prepuzio', true, '300', 85);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (238,'186.9', 'Testicolo', true, '310', 84);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (239,'186.0', 'Testicolo ritenuto', true, '300', 84);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (240,'185.0', 'Prostata', true, '300', 83);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (241,'184.4', 'Vulva', true, '310', 82);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (242,'184.0', 'Vagina', true, '300', 82);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (243,'183.0', 'Ovaio', true, '300', 81);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (244,'181.0', 'Placenta', true, '300', 99);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (245,'179.0', 'Utero, N.A.S.', true, '300', 77);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (246,'174.9', 'Mammella, N.A.S.', true, '300', 190);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (247,'173.9', 'Cute, N.A.S.', true, '330', 189);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (248,'173.5', 'Cute del tronco e coda', true, '320', 189);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (249,'173.3', 'Cute muso', true, '310', 189);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (250,'173.2', 'Cute padiglione auricolare', true, '300', 189);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (251,'171.4', 'Parti molli torace (diaframma)', true, '360', 188);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (252,'170.9', 'Osso, N.A.S.', true, '350', 187);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (253,'170.8', 'Ossa corte arto posteriore', true, '340', 187);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (254,'170.7', 'Ossa lunghe arto posteriore', true, '330', 187);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (255,'170.5', 'Ossa corte arto anteriore', true, '320', 187);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (256,'170.4', 'Ossa lunghe arto anteriore', true, '310', 187);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (257,'170.3', 'Coste, sterno, clavicola', true, '300', 187);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (258,'170.1', 'Mandibola', true, '310', 187);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (259,'170.0', 'Ossa cranio', true, '300', 187);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (260,'169.2', 'Milza', true, '300', 186);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (261,'164.9', 'Mediastino, N.A.S.', true, '320', 28);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (262,'164.1', 'Cuore', true, '310', 28);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (263,'164.0', 'Timo', true, '300', 28);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (264,'163.9', 'Pleura, N.A.S.', true, '300', 27);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (265,'162.9', 'Polmone N.A.S', true, '310', 26);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (266,'162.2', 'Bronco principale', true, '300', 26);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (267,'162.0', 'Trachea', true, '430', 26);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (268,'161.9', 'Laringe, N.A.S.', true, '420', 25);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (269,'160.1', 'Orecchio medio ed interno', true, '410', 24);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (270,'160.0', 'Cavità nasale', true, '400', 24);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (271,'158.9', 'Peritoneo, N.A.S.', true, '390', 21);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (272,'157.9', 'Pancreas, N.A.S.', true, '380', 20);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (273,'156.9', 'Vie biliari, N.A.S.', true, '370', 19);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (274,'156.0', 'Colecisti', true, '360', 19);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (275,'155.0', 'Fegato primario', true, '350', 18);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (276,'154.3', 'Ano', true, '340', 17);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (277,'154.1', 'Retto', true, '330', 17);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (278,'153.9', 'Colon, N.A.S.', true, '320', 16);
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (279,'153.4', 'Cieco', true, '310', 16);


update lookup_esame_istopatologico_sede_lesione set enabled = false where id in (6,9);
update lookup_esame_istopatologico_sede_lesione set padre = 13 where description ilike  '%esofago cervicale%';
update lookup_esame_istopatologico_sede_lesione set padre = 13 where description ilike  '%Esofago toracico%';
update lookup_esame_istopatologico_sede_lesione set padre = 13 where description ilike  '%Esofago addominale%';
update lookup_esame_istopatologico_sede_lesione set padre = 13 where description ilike  '%Esofago N.A.S.%';
update lookup_esame_istopatologico_sede_lesione set padre = null, enabled = false where id = 22;
update lookup_esame_istopatologico_sede_lesione set padre = null, enabled = false where id = 29;
update lookup_esame_istopatologico_sede_lesione set description = 'Cavit&agrave; nasale' where id = 270;
update lookup_esame_istopatologico_sede_lesione set padre = 93 where id = 229;
update lookup_esame_istopatologico_sede_lesione set enabled = false, padre = null where id = 6;
update lookup_esame_istopatologico_sede_lesione set enabled = false, padre = null where id = 9;
update lookup_esame_istopatologico_sede_lesione set enabled = false, padre = null where id = 10;
update lookup_esame_istopatologico_sede_lesione set enabled = false, padre = null where id = 11;
update lookup_esame_istopatologico_sede_lesione set enabled = false, padre = null where (id >= 34 and id <= 73);
update lookup_esame_istopatologico_sede_lesione set enabled = false, padre = null where id = 78;
update lookup_esame_istopatologico_sede_lesione set enabled = false, padre = null where id = 80;
update lookup_esame_istopatologico_sede_lesione set padre = 79 where codice = '181.0';
update lookup_esame_istopatologico_sede_lesione set enabled = false, padre = null where id = 91
update lookup_esame_istopatologico_sede_lesione set level = 290 where id = 267;
update lookup_esame_istopatologico_sede_lesione set level = 320 where id = 257;
update lookup_esame_istopatologico_sede_lesione set level = 330 where id = 256;
update lookup_esame_istopatologico_sede_lesione set level = 340 where id = 255;
update lookup_esame_istopatologico_sede_lesione set level = 350 where id = 254;
update lookup_esame_istopatologico_sede_lesione set level = 360 where id = 253;
update lookup_esame_istopatologico_sede_lesione set level = 370 where id = 252;
INSERT INTO lookup_esame_istopatologico_sede_lesione(id,codice, description, enabled, level, padre) VALUES (280,'171', 'Tessuto Connettivo, Sottocutaneo E Altre Parti Molli', true, '355', 188);

--Piano di Monitoraggio Fauna Selvatica - Piano B7A
INSERT INTO lookup_accettazione_attivita_esterna(description, enabled, effettuabile_da_morto) 
VALUES ( 'Piano di Monitoraggio Fauna Selvatica (Piano B7 A)', true, true);
update lookup_operazioni_accettazione set sinantropi = true where description ilike '%esterne%';
update lookup_operazioni_accettazione set description = 'Trasporto Spoglie (Attivita'' B6/B7)' where description ilike '%Trasporto Spoglie%';

INSERT INTO lookup_tipi_richiedente(id, description, enabled, forza_pubblica, level) VALUES (16, 'Capitaneria di Porto', true, true, 150);









--Richieste istopatologici per LLPP


 -- View: utenti_super

-- DROP VIEW utenti_super;

CREATE OR REPLACE VIEW utenti_super AS 
 SELECT utenti_super_.id,
    utenti_super_.data_scadenza,
    utenti_super_.enabled,
    utenti_super_.enabled_date,
    utenti_super_.entered,
    utenti_super_.entered_by,
    utenti_super_.modified,
    utenti_super_.modified_by,
    utenti_super_.note,
    utenti_super_.password,
    utenti_super_.trashed_date,
    utenti_super_.username,
    utenti_super_.access_position_lat,
    utenti_super_.access_position_lon,
    utenti_super_.access_position_err,
    utenti_super_.last_login,
    utenti_super_.luogo,
    utenti_super_.num_iscrizione_albo,
    utenti_super_.sigla_provincia
   FROM utenti_super_
  WHERE (utenti_super_.id IN ( SELECT utenti.superutente
           FROM utenti));

ALTER TABLE utenti_super
  OWNER TO postgres;
  
  
  
  
  -- View: guc_utenti

-- DROP VIEW guc_utenti;

CREATE OR REPLACE VIEW guc_utenti AS 
 SELECT aa.id,
    aa.clinica_description,
    aa.clinica_id,
    aa.codice_fiscale,
    aa.cognome,
    aa.email,
    aa.enabled,
    aa.entered,
    aa.enteredby,
    aa.expires,
    aa.modified,
    aa.modifiedby,
    aa.nome,
    aa.note,
    aa.password,
    aa.username,
    aa.asl_id,
    aa.canile_id,
    aa.canile_description,
    aa.password_encrypted,
    aa.luogo,
    aa.num_autorizzazione,
    aa.importatori_description,
    aa.canilebdu_id,
    aa.canilebdu_description,
    aa.id_importatore,
    aa.id_provincia_iscrizione_albo_vet_privato,
    aa.nr_iscrione_albo_vet_privato,
    aa.password2,
    aa.data_fine_validita,
    aa.data_scadenza,
    aa.id_utente_guc_old,
    aa.suap_ip_address,
    aa.suap_istat_comune,
    aa.suap_pec,
    aa.suap_callback,
    aa.suap_shared_key,
    aa.suap_callback_ko,
    aa.num_registrazione_stab,
    aa.suap_livello_accreditamento,
    aa.telefono,
    aa.data_ultima_modifica_password,
    aa.luogo_vam,
    aa.id_provincia_iscrizione_albo_vet_privato_vam,
    aa.nr_iscrione_albo_vet_privato_vam
   FROM ( SELECT DISTINCT ON (a.username) a.username AS username_,
            a.id,
            a.clinica_description,
            a.clinica_id,
            a.codice_fiscale,
            a.cognome,
            a.email,
            a.enabled,
            a.entered,
            a.enteredby,
            a.expires,
            a.modified,
            a.modifiedby,
            a.nome,
            a.note,
            a.password,
            a.username,
            a.asl_id,
            a.canile_id,
            a.canile_description,
            a.password_encrypted,
            a.luogo,
            a.num_autorizzazione,
            a.importatori_description,
            a.canilebdu_id,
            a.canilebdu_description,
            a.id_importatore,
            a.id_provincia_iscrizione_albo_vet_privato,
            a.nr_iscrione_albo_vet_privato,
            a.password2,
            a.data_fine_validita,
            a.data_scadenza,
            a.id_utente_guc_old,
            a.suap_ip_address,
            a.suap_istat_comune,
            a.suap_pec,
            a.suap_callback,
            a.suap_shared_key,
            a.suap_callback_ko,
            a.num_registrazione_stab,
            a.suap_livello_accreditamento,
            a.telefono,
            a.data_ultima_modifica_password,
            a.luogo_vam,
            a.id_provincia_iscrizione_albo_vet_privato_vam,
            a.nr_iscrione_albo_vet_privato_vam
           FROM ( SELECT guc_utenti_.id,
                    guc_utenti_.clinica_description,
                    guc_utenti_.clinica_id,
                    guc_utenti_.codice_fiscale,
                    guc_utenti_.cognome,
                    guc_utenti_.email,
                    guc_utenti_.enabled,
                    guc_utenti_.entered,
                    guc_utenti_.enteredby,
                    guc_utenti_.expires,
                    guc_utenti_.modified,
                    guc_utenti_.modifiedby,
                    guc_utenti_.nome,
                    guc_utenti_.note,
                    guc_utenti_.password,
                    guc_utenti_.username,
                    guc_utenti_.asl_id,
                    guc_utenti_.canile_id,
                    guc_utenti_.canile_description,
                    guc_utenti_.password_encrypted,
                    guc_utenti_.luogo,
                    guc_utenti_.num_autorizzazione,
                    guc_utenti_.importatori_description,
                    guc_utenti_.canilebdu_id,
                    guc_utenti_.canilebdu_description,
                    guc_utenti_.id_importatore,
                    guc_utenti_.id_provincia_iscrizione_albo_vet_privato,
                    guc_utenti_.nr_iscrione_albo_vet_privato,
                    guc_utenti_.password2,
                    guc_utenti_.data_fine_validita,
                    guc_utenti_.data_scadenza,
                    guc_utenti_.id_utente_guc_old,
                    guc_utenti_.suap_ip_address,
                    guc_utenti_.suap_istat_comune,
                    guc_utenti_.suap_pec,
                    guc_utenti_.suap_callback,
                    guc_utenti_.suap_shared_key,
                    guc_utenti_.suap_callback_ko,
                    guc_utenti_.num_registrazione_stab,
                    guc_utenti_.suap_descrizione_livello_accreditamento,
                    guc_utenti_.suap_livello_accreditamento,
                    guc_utenti_.telefono,
                    guc_utenti_.data_ultima_modifica_password,
                    guc_utenti_.luogo_vam,
                    guc_utenti_.id_provincia_iscrizione_albo_vet_privato_vam,
                    guc_utenti_.nr_iscrione_albo_vet_privato_vam
                   FROM guc_utenti_
                  WHERE guc_utenti_.data_scadenza > 'now'::text::date::text::date OR guc_utenti_.data_scadenza IS NULL AND guc_utenti_.enabled) a
          ORDER BY a.username, a.data_scadenza) aa;

ALTER TABLE guc_utenti
  OWNER TO postgres;
  
  
  
  
  
  
  CREATE OR REPLACE FUNCTION public.dbi_insert_utente(
    usr character varying,
    password character varying,
    role_id integer,
    enteredby integer,
    modifiedby integer,
    enabled boolean,
    site_id integer,
    namefirst character varying,
    namelast character varying,
    cf character varying,
    notes text,
    luogo text,
    nickname character varying,
    email character varying,
    expires timestamp with time zone,
    clinica integer,
    luogo_vam text,
    id_provincia integer,
    nr_iscrizione text)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
us_id2 int ;
t timestamp without time zone;
asl_val int;
provincia text;
cat_name text;
BEGIN

	IF (site_id=-1) THEN
	   asl_val=null;
	ELSE
	   asl_val=site_id;
	END IF;

	IF (role_id=-1) THEN
		enabled:=false;
	ELSE
		enabled:=true;
	END IF;

	IF (clinica=-1) THEN
		clinica:=null;
	END IF;

	t=now();

	--Gestione Province
        provincia:='';
	IF (id_provincia=61) THEN
	   provincia:='CE';
	ELSIF(id_provincia=62) THEN
	   provincia:='BN';
	ELSIF(id_provincia=63) THEN
	   provincia:='NA';
	ELSIF(id_provincia=64) THEN
	   provincia:='AV';
	ELSIF(id_provincia=65) THEN
	   provincia:='SA';
	END IF;
	--Fine Gestione Province

	
	us_id := (select us.id from utenti_super us where us.username = usr and us.trashed_date is null);
	IF (us_id is null) THEN	
		us_id=nextVal('utenti_super_id_seq');
		INSERT INTO utenti_super (id, data_scadenza,enabled,entered,modified,note,password,username,num_iscrizione_albo, sigla_provincia,luogo) 
		VALUES (us_id,expires::timestamp without time zone,enabled,t,t,notes,password,usr,nr_iscrizione,provincia,luogo_vam);
	END IF;

	IF (clinica is not null) THEN
		us_id2=nextVal('utenti_id_seq');
		INSERT INTO utenti (id,codice_fiscale,cognome,data_scadenza,email,enabled,entered,nome,note,password,ruolo,username,clinica,superutente,asl_referenza) 
		VALUES (us_id2,cf,namelast,expires::timestamp without time zone,email,enabled,t,namefirst,notes,password,role_id,usr,clinica,us_id,asl_val); 

		INSERT INTO secureobject (name) VALUES (us_id2::text);
		
		cat_name := (select nome from permessi_ruoli where id=role_id);
		INSERT INTO category_secureobject (categories_name,secureobjects_name) 
		VALUES (cat_name,us_id2::text);
	END IF;
	
	msg = 'OK';
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_insert_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, integer)
  OWNER TO postgres;
  
  
  
  
  
  
  CREATE OR REPLACE FUNCTION public.dbi_cambia_profilo_utente(
    usr character varying,
    password character varying,
    role_id integer,
    enteredby integer,
    modifiedby integer,
    enabled boolean,
    site_id integer,
    namefirst character varying,
    namelast character varying,
    cf character varying,
    notes text,
    luogo text,
    nickname character varying,
    email character varying,
    expires timestamp with time zone,
    clinica text,
    luogo_vam text,
    id_provincia integer,
    nr_iscrizione text,
    datascadenza timestamp with time zone,
    flagcessato boolean)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
con_id int;
us_id2 int ;
flag boolean;
asl_val int;
provincia text;
t timestamp without time zone;
cat_name text;
utenteid int;
i integer;
ids INT[];

     
   
BEGIN
	       --utenteid := (select id from utenti us where username = usr and data_scadenza is null and trashed_date is null);
	       update utenti_super_ set data_scadenza = datascadenza where username = usr and data_scadenza is null and trashed_date is null ;
	       update utenti_ set data_scadenza = datascadenza where username = usr and data_scadenza is null and trashed_date is null ;
	       
	       --delete from category_secureobject where secureobjects_name = utenteid::text;
	       --delete from secureobject where name = utenteid::text;
	
		if (flagcessato=false)
		then	       
		
IF (site_id=-1) THEN
	   asl_val=null;
	ELSE
	   asl_val=site_id;
	END IF;

	IF (role_id=-1) THEN
		enabled:=false;
	ELSE
		enabled:=true;
	END IF;

	--IF (clinica=-1) THEN
	--	clinica:=null;
	--END IF;

	t=now();


        --Gestione Province
        provincia := '';
	IF (id_provincia=61) THEN
	   provincia:='CE';
	ELSIF(id_provincia=62) THEN
	   provincia:='BN';
	ELSIF(id_provincia=63) THEN
	   provincia:='NA';
	ELSIF(id_provincia=64) THEN
	   provincia:='AV';
	ELSIF(id_provincia=65) THEN
	   provincia:='SA';
	END IF;
	--Fine Gestione Province

	IF (role_id=-1) THEN
		enabled:=false;
	ELSE
		enabled:=true;
	END IF;

	
	--us_id := (select us.id from utenti_super us where us.username = usr and us.trashed_date is null);
	--IF (us_id is null) THEN	
		us_id=nextVal('utenti_super_id_seq');
		INSERT INTO utenti_super (id, data_scadenza,enabled,entered,modified,note,password,username,num_iscrizione_albo,sigla_provincia,luogo) 
		VALUES (us_id,expires::timestamp without time zone,enabled,t,t,notes,password,usr,nr_iscrizione,provincia,luogo_vam);
	--END IF;

	--IF (clinica is not null) THEN
	  ids = string_to_array(clinica,',');
	FOREACH i IN ARRAY ids
		LOOP
		RAISE NOTICE '%', i;
		--RAISE NOTICE '%', ids[i];
		us_id2=nextVal('utenti_id_seq');
		INSERT INTO utenti (id,codice_fiscale,cognome,data_scadenza,email,enabled,entered,nome,note,password,ruolo,username,clinica,superutente,asl_referenza) 
		VALUES (us_id2,cf,namelast,expires::timestamp without time zone,email,enabled,t,namefirst,notes,password,role_id,usr,i,us_id,asl_val); 

		INSERT INTO secureobject (name) VALUES (us_id2::text);
		
		cat_name := (select nome from permessi_ruoli where id=role_id);
		INSERT INTO category_secureobject (categories_name,secureobjects_name) 
		VALUES (cat_name,us_id2::text);
	END LOOP;
	--END IF;


		
		end if;
	
	msg = 'OK';
	RETURN msg;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_cambia_profilo_utente(character varying, character varying, integer, integer, integer, boolean, integer, character varying, character varying, character varying, text, text, character varying, character varying, timestamp with time zone, text, timestamp with time zone, boolean)
  OWNER TO postgres;
  
  
  
  
  -- Function: public.dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text)

-- DROP FUNCTION public.dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text);

CREATE OR REPLACE FUNCTION public.dbi_insert_utente_guc(
    input_cf text,
    input_cognome text,
    input_email text,
    input_enabled boolean,
    input_enteredby integer,
    input_expires timestamp without time zone,
    input_modifiedby integer,
    input_note text,
    input_nome text,
    input_password text,
    input_username text,
    input_asl_id integer,
    input_password_encrypted text,
    input_canile_id integer,
    input_canile_description text,
    input_luogo text,
    input_num_autorizzazione text,
    input_id_importatore integer,
    input_canilebdu_id integer,
    input_canilebdu_description text,
    input_importatori_description text,
    input_id_provincia_iscrizione_albo_vet_privato integer,
    input_nr_iscrione_albo_vet_privato text,
    input_id_utente_guc_old integer,
    input_suap_ip_address text,
    input_suap_istat_comune text,
    input_suap_pec text,
    input_suap_callback text,
    input_suap_shared_key text,
    input_suap_callback_ko text,
    input_num_registrazione_stab text,
    input_suap_livello_accreditamento integer,
    input_suap_descrizione_livello_accreditamento text,
    input_telefono text,
    input_ruolo_id_gisa integer,
    input_ruolo_descrizione_gisa text,
    input_ruolo_id_gisa_ext integer,
    input_ruolo_descrizione_gisa_ext text,
    input_ruolo_id_bdu integer,
    input_ruolo_descrizione_bdu text,
    input_ruolo_id_vam integer,
    input_ruolo_descrizione_vam text,
    input_ruolo_id_importatori integer,
    input_ruolo_descrizione_importatori text,
    input_ruolo_id_digemon integer,
    input_ruolo_descrizione_digemon text,
    input_luogo_vam text,
    input_id_provincia_vam integer,
    input_nr_iscrizione_vam text)
  RETURNS text AS
$BODY$
   DECLARE
msg text ;
us_id int ;
ruolo_gisa guc_ruolo;
ruolo_gisa_ext guc_ruolo;
ruolo_bdu guc_ruolo;
ruolo_vam guc_ruolo;
ruolo_importatori guc_ruolo;
ruolo_digemon guc_ruolo;

BEGIN


--Inserisco utente
us_id:= (select nextval('guc_utenti_id_seq'));
INSERT INTO guc_utenti_ (ID,CODICE_FISCALE,COGNOME,EMAIL,ENABLED,entered,enteredby,expires,modified,modifiedby,note,nome,password,username,asl_id,password_encrypted,canile_id, canile_description,luogo,num_autorizzazione,id_importatore,canilebdu_id, canilebdu_description,importatori_description,id_provincia_iscrizione_albo_vet_privato,nr_iscrione_albo_vet_privato,id_utente_guc_old,suap_ip_address ,suap_istat_comune ,suap_pec ,suap_callback ,suap_shared_key,suap_callback_ko,num_registrazione_stab,suap_livello_accreditamento,suap_descrizione_livello_accreditamento,telefono, luogo_vam, id_provincia_iscrizione_albo_vet_privato_vam, nr_iscrione_albo_vet_privato_vam  ) 
values (us_id,input_cf,input_cognome,input_email,input_enabled,now(),input_enteredby,input_expires,now(),input_modifiedby,input_note, input_nome,input_password,input_username,input_asl_id,input_password_encrypted,input_canile_id, input_canile_description,input_luogo,input_num_autorizzazione,input_id_importatore,input_canilebdu_id, input_canilebdu_description,input_importatori_description,input_id_provincia_iscrizione_albo_vet_privato,input_nr_iscrione_albo_vet_privato,input_id_utente_guc_old,input_suap_ip_address ,input_suap_istat_comune ,input_suap_pec ,input_suap_callback ,input_suap_shared_key,input_suap_callback_ko,input_num_registrazione_stab,input_suap_livello_accreditamento,input_suap_descrizione_livello_accreditamento,input_telefono , input_luogo_vam, input_id_provincia_vam, input_nr_iscrizione_vam );

msg:='OK';


--Popolo ruoli e li inserisco

ruolo_gisa.id_ruolo:= input_ruolo_id_gisa;
ruolo_gisa.descrizione_ruolo:= input_ruolo_descrizione_gisa;
ruolo_gisa.end_point:= 'Gisa';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa.end_point,ruolo_gisa.id_ruolo,ruolo_gisa.descrizione_ruolo,us_id,input_note);

ruolo_gisa_ext.id_ruolo:= input_ruolo_id_gisa_ext;
ruolo_gisa_ext.descrizione_ruolo:= input_ruolo_descrizione_gisa_ext;
ruolo_gisa_ext.end_point:= 'Gisa_ext';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_gisa_ext.end_point,ruolo_gisa_ext.id_ruolo,ruolo_gisa_ext.descrizione_ruolo,us_id,input_note);

ruolo_bdu.id_ruolo:= input_ruolo_id_bdu;
ruolo_bdu.descrizione_ruolo:= input_ruolo_descrizione_bdu;
ruolo_bdu.end_point:= 'bdu';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_bdu.end_point,ruolo_bdu.id_ruolo,ruolo_bdu.descrizione_ruolo,us_id,input_note);

ruolo_vam.id_ruolo:= input_ruolo_id_vam;
ruolo_vam.descrizione_ruolo:= input_ruolo_descrizione_vam;
ruolo_vam.end_point:= 'Vam';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_vam.end_point,ruolo_vam.id_ruolo,ruolo_vam.descrizione_ruolo,us_id,input_note);

ruolo_importatori.id_ruolo:= input_ruolo_id_importatori;
ruolo_importatori.descrizione_ruolo:= input_ruolo_descrizione_importatori;
ruolo_importatori.end_point:= 'Importatori';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_importatori.end_point,ruolo_importatori.id_ruolo,ruolo_importatori.descrizione_ruolo,us_id,input_note);

ruolo_digemon.id_ruolo:= input_ruolo_id_digemon;
ruolo_digemon.descrizione_ruolo:= input_ruolo_descrizione_digemon;
ruolo_digemon.end_point:= 'Digemon';

INSERT INTO guc_ruoli (endpoint,ruolo_integer,ruolo_string,id_utente,note) VALUES (ruolo_digemon.end_point,ruolo_digemon.id_ruolo,ruolo_digemon.descrizione_ruolo,us_id,input_note);

	
	
	RETURN msg||'_'||us_id;

END
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public.dbi_insert_utente_guc(text, text, text, boolean, integer, timestamp without time zone, integer, text, text, text, text, integer, text, integer, text, text, text, integer, integer, text, text, integer, text, integer, text, text, text, text, text, text, text, integer, text, text, integer, text, integer, text, integer, text, integer, text, integer, text, integer, text)
  OWNER TO postgres;
  
  
  
  
  
  
  
  
  
  
  
  --Gestione Flussi Richiesta/Esito Necroscopia
--cavità addominale
update lookup_autopsia_organi set level = 128 where description ilike 'cavit%addom%';

--cavità nasali
update lookup_autopsia_organi set level = 120 where id = 59;

--Naso
update lookup_autopsia_organi set level = 118 where id = 58;

--Orecchio Esterno
update lookup_autopsia_organi set level = 115 where id = 75;

insert into lookup_autopsia_organi(description, enabled,enabled_sde,level,level_sde,tessuto,cani,gatti,uccelli,mammiferi,rettili) 
values ('Faringe',                                true,   true,      112, 95,         false,true,true,false,true,false);
insert into lookup_autopsia_organi(description, enabled,enabled_sde,level,level_sde,tessuto,cani,gatti,uccelli,mammiferi,rettili) 
values ('Laringe',                                true,   true,      113, 145,         false,true,true,false,true,false);



insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 1);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 2);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 3);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 4);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 5);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 6);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 7);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 8);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 9);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 10);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 135);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'faringe%'), 136);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 1);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 2);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 3);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 4);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 5);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 6);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 7);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 8);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 9);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 10);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 135);
insert into organi_patologieprevalenti(organo, patologia_prevalente) values((select id from lookup_autopsia_organi where description ilike 'laringe'), 136);

ALTER TABLE public.autopsia ADD COLUMN modalita_conservazione_richiesta integer;
ALTER TABLE public.autopsia ADD COLUMN temperatura_conservazione_richiesta real;
alter table autopsia add column progressivo integer;


--Modifiche del 14/06/2016
ALTER TABLE public.autopsia ADD COLUMN entered_by_esito integer;
ALTER TABLE public.autopsia ADD COLUMN entered_esito timestamp without time zone;
ALTER TABLE public.autopsia ADD COLUMN modified_by_esito integer;
ALTER TABLE public.autopsia ADD COLUMN modified_esito timestamp without time zone;

-- Rule: guc_utenti_update ON guc_utenti

-- DROP RULE guc_utenti_update ON guc_utenti;

CREATE OR REPLACE RULE guc_utenti_update AS
    ON UPDATE TO guc_utenti DO INSTEAD  UPDATE guc_utenti_ SET password = new.password, data_ultima_modifica_password = new.data_ultima_modifica_password
  WHERE guc_utenti_.id = new.id;


--ADOZIONE VERSO ASSOCIAZIONI/CANILI
--VAM
ALTER TABLE public.accettazione     ADD COLUMN adozione_verso_assoc_canili boolean;
ALTER TABLE public.cartella_clinica ADD COLUMN adozione_verso_assoc_canili boolean;
ALTER TABLE public.lookup_operazioni_accettazione ADD COLUMN verso_assoc_canili boolean;
update lookup_operazioni_accettazione set verso_assoc_canili = true where id = 2;

--BDU
DROP FUNCTION public_functions.getlistaregistrazionicane(text, integer);
DROP FUNCTION public_functions.getlistaregistrazionigatto(text);
DROP FUNCTION public_functions.getlistaregistrazionigatto(text, integer);


-- Type: public_functions.lista_registrazioni

DROP TYPE public_functions.lista_registrazioni;

CREATE TYPE public_functions.lista_registrazioni AS
   (adozione boolean,
    ricattura boolean,
    cessione boolean,
    controllcomm boolean,
    controlli boolean,
    decesso boolean,
    furto boolean,
    gestcessione boolean,
    passaporto boolean,
    rinnovo_passaporto boolean,
    presacessione boolean,
    reimmissione boolean,
    restituisci boolean,
    rientro boolean,
    ritrovamento boolean,
    ritrovamentosmarrnondenunciato boolean,
    smarrimento boolean,
    trasfcanile boolean,
    ritornoproprietario boolean,
    trasfpropcanile boolean,
    trasfregione boolean,
    sterilizzazione boolean,
    trasferimento boolean,
    prelievodna boolean,
    adozionefa boolean,
    adozioneAssocCanili boolean,
    trasfresidenzaprop boolean,
    restituzionecanileorigine boolean,
    prelievoleishmania boolean,
    ritornoaslorigine boolean);
ALTER TYPE public_functions.lista_registrazioni
  OWNER TO postgres;


-- Type: public_functions.lista_registrazioni_gatto

DROP TYPE public_functions.lista_registrazioni_gatto;

CREATE TYPE public_functions.lista_registrazioni_gatto AS
   (adozione boolean,
    decesso boolean,
    furto boolean,
    ricattura boolean,
    ritrovamento boolean,
    ritrovamentosmarrnondenunciato boolean,
    smarrimento boolean,
    trasferimento boolean,
    passaporto boolean,
    rinnovo_passaporto boolean,
    reimmissione boolean,
    sterilizzazione boolean,
    adozionefa boolean,
    adozioneAssocCanili boolean,
    trasfresidenzaprop boolean,
    trasfregione boolean,
    ritornoproprietario boolean,
    trasfcanile boolean,
    cessione boolean,
    restituzionecanileorigine boolean,
    prelievoleishmania boolean,
    ritornoaslorigine boolean);
ALTER TYPE public_functions.lista_registrazioni_gatto
  OWNER TO postgres;



CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionigatto(
    microchip_to_get text,
    asl_utente integer)
  RETURNS public_functions.lista_registrazioni_gatto AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni_gatto;
tipologia_registrazione integer;
data_vaccinazione_antirabbia timestamp without time zone;
--numero_giorni_da_vaccinazione integer;
passaporto_corrente character varying;
animale_fuori_dominio boolean;
asl_animale integer;
asl_in_carico integer;
query_da_eseguire character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false into  record_to_return;

select a.flag_ultima_operazione_eseguita_fuori_dominio_asl, a.id_asl_riferimento, a.id_asl_fuori_dominio_ultima_registrazione
into animale_fuori_dominio, asl_animale, asl_in_carico
from animale a
where (microchip = microchip_to_get or tatuaggio = microchip_to_get) and
      trashed_date is null;



      IF (animale_fuori_dominio=false) THEN
query_da_eseguire := 'select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join gatto g on (a.id = g.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2)';
END IF;

IF (asl_utente=asl_in_carico) THEN
query_da_eseguire := 'Select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto from registrazioni_wk wk inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join gatto g on (a.id = g.id_animale)
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2) and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true and only_hd = false ';
END IF;

IF (asl_utente=asl_animale) THEN
query_da_eseguire := 'Select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto  from registrazioni_wk wk inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2) and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true  
and l.effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true and only_hd = false ';

END IF;

IF (asl_utente!=asl_animale and asl_utente!=asl_in_carico) THEN
--da gestire il caso di utenti non di asl di apparteneza animale e non di asl in carico
query_da_eseguire := '';

END IF;

open cursore for execute query_da_eseguire;

LOOP

fetch cursore into tipologia_registrazione, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND; 


/*Calcolo giorni da ultima vaccinazione antirabbia
select (to_date(to_char(current_timestamp, 'YYYY/MM/DD'),  'YYYY/MM/DD') - 
to_date( to_char( data_vaccinazione_antirabbia, 'YYYY/MM/DD'), 'YYYY/MM/DD')) into  numero_giorni_da_vaccinazione;*/


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione


WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;



WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;


WHEN 19 THEN /*adozione*/
record_to_return.adozione := true;

WHEN 48 THEN /*adozione*/
if (passaporto_corrente is not null and passaporto_corrente != '') then
record_to_return.rinnovo_passaporto := true;
end if;

WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;

WHEN 45 THEN /*restituzione a proprietario*/
record_to_return.ritornoProprietario := true;

WHEN 6 THEN /*passaporto*/
if /*((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/
(passaporto_corrente is null or passaporto_corrente = '') then
record_to_return.passaporto := true;
end if;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 24 THEN /*ricattura*/
record_to_return.ricattura := true;

WHEN 46 THEN /*adozione fuori asl / fuori regione*/
record_to_return.adozionefa := true;

WHEN 61 THEN /* ADOZIONE VERSO ASSOCIAZIONI E CANILI*/
record_to_return.adozioneAssocCanili := true;

WHEN 43 THEN /*modifica residenza proprietario o detentore*/
record_to_return.trasfresidenzaprop := true;

WHEN 7 THEN /*Cessione*/
record_to_return.cessione := true;

WHEN 8 THEN /*trasferimento fuori regione*/
record_to_return.trasfregione := true;

WHEN 54 THEN /*prelievo leishmania*/
record_to_return.prelievoleishmania := true;

WHEN 55 THEN /*Ritorno ad Asl di Origine*/
record_to_return.ritornoAslOrigine := true;

ELSE 

END CASE;

/* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionigatto(text, integer)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text, integer) TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text, integer) TO public;

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionicane(
    microchip_to_get text,
    asl_utente integer)
  RETURNS public_functions.lista_registrazioni AS
$BODY$
DECLARE  
cursore	refcursor;
cursore2 refcursor;
cursore3 refcursor;
record_to_return public_functions.lista_registrazioni;
tipologia_registrazione integer;
flag_prelievo boolean;
data_vaccinazione_antirabbia timestamp without time zone;
/*numero_giorni_da_vaccinazione integer;*/
passaporto_corrente character varying;
id_asl_animale integer;
flag_ultima_operazione_eseguita_fuori_dominio_asl_var boolean;
id_asl_fuori_dominio_ultima_registrazione_var integer;
id_tipologia_registrazione_fuori_dominio_asl_var integer;

checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl boolean;
flagIsAslProprietariaConAnimaleFuoriDominioAsl boolean;
flagIsUtenteInCaricoPerFuoriDominio boolean;
flagIsAslDiversaConAnimaleFuoriDominioAsl boolean;
passaporto_numero_var text;

BEGIN
select false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false, false, false, false, false, false, false, false, false into  record_to_return;

raise info 'ciao';

open cursore3 for select id_asl_riferimento, 
flag_ultima_operazione_eseguita_fuori_dominio_asl,
id_asl_fuori_dominio_ultima_registrazione,
id_tipologia_registrazione_fuori_dominio_asl,
passaporto_numero
  from animale a where 
(a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and data_cancellazione is null and trashed_date is null;
LOOP
fetch cursore3 into id_asl_animale, flag_ultima_operazione_eseguita_fuori_dominio_asl_var, id_asl_fuori_dominio_ultima_registrazione_var, id_tipologia_registrazione_fuori_dominio_asl_var, passaporto_numero_var;
EXIT WHEN NOT FOUND;


if (flag_ultima_operazione_eseguita_fuori_dominio_asl_var =  true) then
checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl:=true;
end if;

if (flag_ultima_operazione_eseguita_fuori_dominio_asl_var =  true and asl_utente = id_asl_animale) then
raise info 'ASL UTENTE';
flagIsAslProprietariaConAnimaleFuoriDominioAsl := true;
end if;

if (flag_ultima_operazione_eseguita_fuori_dominio_asl_var =  true and asl_utente = id_asl_fuori_dominio_ultima_registrazione_var) then
raise info 'ASL IN CARICO';
flagIsUtenteInCaricoPerFuoriDominio := true;
end if;

if (flag_ultima_operazione_eseguita_fuori_dominio_asl_var =  true and asl_utente != id_asl_animale and asl_utente != id_asl_fuori_dominio_ultima_registrazione_var ) then
raise info 'ASL DIVERSA';
flagIsAslDiversaConAnimaleFuoriDominioAsl := true;
end if;


raise info 'safasf % % % %', checkSoloRegistrazioniEffettuabiliConAnimaleFuoriDominioAsl, flagIsUtenteInCaricoPerFuoriDominio, flagIsAslProprietariaConAnimaleFuoriDominioAsl, flagIsAslDiversaConAnimaleFuoriDominioAsl;



open cursore for select wk.id_registrazione, c.flag_prelievo_dna, a.vaccino_data, a.numero_passaporto, a.id_asl_riferimento  from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) 
inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)
left join cane c on (a.id = c.id_animale)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) 
and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 1)
and ((flag_ultima_operazione_eseguita_fuori_dominio_asl_var = true 
and flagIsAslProprietariaConAnimaleFuoriDominioAsl = true ) and effettuabile_con_animale_fuori_dominio_asl_da_asl_proprietaria = true 
or (flag_ultima_operazione_eseguita_fuori_dominio_asl_var is not true
or flagIsAslProprietariaConAnimaleFuoriDominioAsl is not true  )
)

and ((flag_ultima_operazione_eseguita_fuori_dominio_asl_var = true 
and flagIsAslDiversaConAnimaleFuoriDominioAsl = true) and effettuabile_con_animale_fuori_dominio_asl_da_asl_diversa = true
or (flag_ultima_operazione_eseguita_fuori_dominio_asl_var is not true 
or flagIsAslDiversaConAnimaleFuoriDominioAsl is not true) )

and ((flag_ultima_operazione_eseguita_fuori_dominio_asl_var = true 
and flagIsUtenteInCaricoPerFuoriDominio = true) and effettuabile_con_animale_fuori_dominio_asl_da_asl_in_carico = true
or (flag_ultima_operazione_eseguita_fuori_dominio_asl_var is not true
or flagIsUtenteInCaricoPerFuoriDominio is not true));


LOOP

fetch cursore into tipologia_registrazione, flag_prelievo, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND;


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione

WHEN 24 then /* RICATTURA*/
record_to_return.ricattura := true;

WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;

WHEN 48 then /*FURTO*/
raise info 'entrato nel case when 48 val %, numero: %', tipologia_registrazione, passaporto_numero_var;
if(passaporto_numero_var is not null and passaporto_numero_var!='') then record_to_return.rinnovo_passaporto := true;
else record_to_return.rinnovo_passaporto := false;
end if;

WHEN 6 then /*PASSAPORTO*/
raise info 'entrato nel case when 6 val %', tipologia_registrazione;
/*if ((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/ 
if (passaporto_corrente is null or passaporto_corrente = '')
then
record_to_return.passaporto := true;
end if;

WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;


WHEN 7 then /*Cessione*/
record_to_return.cessione := true;
raise info 'entrato nel case when 7 val %', tipologia_registrazione;

WHEN 26 THEN /*Controlli commerciali*/
record_to_return.controllComm := true;

WHEN 22 THEN /*controlli*/
record_to_return.controlli := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;

WHEN 15 THEN /*presa in carico da cessione*/
record_to_return.gestCessione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 31 THEN /*trasferimento a canile*/
if (id_tipologia_registrazione_fuori_dominio_asl_var != 8) then
record_to_return.trasfCanile := true;
end if;

WHEN 45 THEN /*restituzione a proprietario*/
record_to_return.ritornoProprietario := true;

WHEN 8 THEN /*trasferimento fr*/
record_to_return.trasfRegione := true;

WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;

WHEN 17 THEN /*trasferimento*/
record_to_return.rientro := true;


WHEN 13 THEN
record_to_return.adozione := true;


WHEN 41 THEN  /* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

WHEN 50 THEN /* PRELIEVO DNA*/
if (flag_prelievo = false or flag_prelievo is NULL) then
record_to_return.prelievodna := true;
end if;

WHEN 46 THEN /* ADOZIONE FUORI ASL, FUORI REGIONE*/
record_to_return.adozionefa := true;

WHEN 61 THEN /* ADOZIONE VERSO ASSOCIAZIONI E CANILI*/
record_to_return.adozioneAssocCanili := true;

WHEN 43 THEN /* MODIFICA RESIDENZA PROPRIETARIO O DETENTORE*/
record_to_return.trasfresidenzaprop := true;

WHEN 54 THEN /* PRELIEVO CAMPIONI LEISHMANIA*/
record_to_return.prelievoleishmania := true;

WHEN 56 THEN /* PRELIEVO CAMPIONI LEISHMANIA*/
record_to_return.restituzionecanileorigine := true;


ELSE 

END CASE;

--END LOOP;
END LOOP;
end loop;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionicane(text, integer)
  OWNER TO postgres;


  

CREATE OR REPLACE FUNCTION public_functions.getlistaregistrazionigatto(microchip_to_get text)
  RETURNS public_functions.lista_registrazioni_gatto AS
$BODY$
DECLARE  
cursore	refcursor;
record_to_return public_functions.lista_registrazioni_gatto;
tipologia_registrazione integer;
data_vaccinazione_antirabbia timestamp without time zone;
--numero_giorni_da_vaccinazione integer;
passaporto_corrente character varying;
BEGIN
select false,false,false,false,false,false,false,false,false,false into  record_to_return;


open cursore for select wk.id_registrazione, a.vaccino_data, a.numero_passaporto from animale a 
left join registrazioni_wk wk on (a.stato = wk.id_stato) inner join lookup_tipologia_registrazione as l on (wk.id_registrazione = l.code)  
where (a.microchip = microchip_to_get or a.tatuaggio = microchip_to_get) and a.trashed_date is null and l.code in (select id_registrazione from mapping_registrazioni_specie_animale where id_specie = 2);

LOOP

fetch cursore into tipologia_registrazione, data_vaccinazione_antirabbia, passaporto_corrente;
EXIT WHEN NOT FOUND; 


/*Calcolo giorni da ultima vaccinazione antirabbia
select (to_date(to_char(current_timestamp, 'YYYY/MM/DD'),  'YYYY/MM/DD') - 
to_date( to_char( data_vaccinazione_antirabbia, 'YYYY/MM/DD'), 'YYYY/MM/DD')) into  numero_giorni_da_vaccinazione;*/


raise info 'entrato nel loop val %', tipologia_registrazione;

CASE tipologia_registrazione


WHEN 4 then /*FURTO*/
raise info 'entrato nel case when 4 val %', tipologia_registrazione;
record_to_return.furto := true;


WHEN 9 THEN /*decesso*/
record_to_return.decesso := true;



WHEN 12 THEN /*ritrovamento*/
record_to_return.ritrovamento := true;

WHEN 11 THEN /*smarrimento*/
record_to_return.smarrimento := true;


WHEN 16 THEN /*trasferimento*/
record_to_return.trasferimento := true;


WHEN 19 THEN /*adozione*/
record_to_return.adozione := true;

WHEN 48 THEN /*adozione*/
record_to_return.rinnovo_passaporto := true;

WHEN 31 THEN /*trasferimento a canila*/
record_to_return.trasfCanile := true;

WHEN 45 THEN /*restituzione a proprietario*/
record_to_return.ritornoProprietario := true;

WHEN 6 THEN /*passaporto*/
if /*((passaporto_corrente is null or passaporto_corrente = '') and numero_giorni_da_vaccinazione < 365)*/
(passaporto_corrente is null or passaporto_corrente = '') then
record_to_return.passaporto := true;
end if;


WHEN 2 THEN /*sterilizz*/
record_to_return.sterilizzazione := true;

WHEN 23 THEN /*reimmissione*/
record_to_return.reimmissione := true;

WHEN 24 THEN /*ricattura*/
record_to_return.ricattura := true;

WHEN 46 THEN /*adozione fuori asl / fuori regione*/
record_to_return.adozionefa := true;

WHEN 61 THEN /* ADOZIONE VERSO ASSOCIAZIONI E CANILI*/
record_to_return.adozioneAssocCanili := true;

WHEN 43 THEN /*modifica residenza proprietario o detentore*/
record_to_return.trasfresidenzaprop := true;

WHEN 7 THEN /*Cessione*/
record_to_return.cessione := true;

WHEN 8 THEN /*trasferimento fuori regione*/
record_to_return.trasfregione := true;

ELSE 

END CASE;

/* Ritrovamento Smarr non denunciato*/
record_to_return.ritrovamentosmarrnondenunciato := true;

END LOOP;
return record_to_return;
END $BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION public_functions.getlistaregistrazionigatto(text)
  OWNER TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO postgres;
GRANT EXECUTE ON FUNCTION public_functions.getlistaregistrazionigatto(text) TO public;

INSERT INTO mapping_registrazioni_specie_animale(id_registrazione, id_specie) VALUES (61, 2);



--Numero Riferimento Mittente
alter table autopsia add column vincolo_unique_chiavi_autopsie boolean default false;

CREATE UNIQUE INDEX unique_chiavi_autopsie
  ON autopsia
  USING btree
  (numero_accettazione_sigla)
  WHERE trashed_date is null and numero_accettazione_sigla is not null and vincolo_unique_chiavi_autopsie is true;

--CAMBIARE A MANO IL DEFAULT A TRUE

  --Query per trovare i numeri riferimento mittente duplicati
select trim(numero_accettazione_sigla), entered from autopsia
where trim(numero_accettazione_sigla) in 
(
  select trim(numero_accettazione_sigla) 
  from autopsia where vincolo_unique_chiavi_autopsie and trashed_date is null and trim(numero_accettazione_sigla) <> '' and trim(numero_accettazione_sigla) is not null
  group by trim(numero_accettazione_sigla)
  having count(trim(numero_accettazione_sigla)) >1
)
order by numero_accettazione_sigla
  
  

--Modulo Fauna Selvatica
INSERT INTO public.lookup_autopsia_patologie_prevalenti(
            id, description, enabled, level, definitiva, esclusivo)
    VALUES (196, 'Raccolta purulenta' , true, 1815, false, false);
insert into organi_patologieprevalenti values(196,53);
insert into organi_patologieprevalenti values(196,52);
  