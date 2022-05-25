/* Per la gestione del pregresso occorre
 aggiungere un nuovo campo '*_old' 
 nelle tabelle organization, ticket ed access ed altre tabelle e spostare in esso i dati contenuti nell'attuale campo asl */

-- creazione campo *_old --
ALTER TABLE organization ADD asl_old integer;
ALTER TABLE ticket ADD asl_old integer;
ALTER TABLE access ADD asl_old integer;
ALTER TABLE comuni ADD codiceistatasl_old character varying(50);
ALTER TABLE comuni ADD codice_old character varying(50);
ALTER TABLE allerte_asl_coinvolte ADD id_asl_old integer;
ALTER TABLE allerte_asl_coinvolte_history ADD asl_old integer;
ALTER TABLE allerte_imprese_coinvolte ADD idasl_old integer;
ALTER TABLE blackberry_devices ADD id_asl_old integer;
ALTER TABLE blackberry_logs ADD id_asl_old integer;
ALTER TABLE contact ADD site_id_old integer;
ALTER TABLE cu_programmazioni_asl ADD id_asl_old integer;
ALTER TABLE decessi_capi ADD asl_old integer;
ALTER TABLE document_store_user_member ADD site_id_old integer;
ALTER TABLE farmacosorveglianza_allegatoi ADD id_asl_old integer;
ALTER TABLE farmacosorveglianza_allegatoii ADD id_asl_old integer;
ALTER TABLE furti_capi ADD asl_old integer;
ALTER TABLE import ADD site_id_old integer;
ALTER TABLE informazioni_sanitarie_allevamenti ADD asl_old integer;
ALTER TABLE ingresso_capi ADD asl_old integer;
ALTER TABLE log ADD asl_id_old integer;
ALTER TABLE log_operazioni ADD asl_id_old integer;
ALTER TABLE m_capi ADD cd_asl_old integer;
ALTER TABLE macellazione_capi ADD asl_old integer;
ALTER TABLE operatori_allevamenti ADD id_asl_old integer;
ALTER TABLE organization_scadenzario ADD site_id_old integer;
ALTER TABLE uscita_capi  ADD asl_old integer;

-- fine --

-- popolamento campo *_old --

UPDATE organization SET asl_old = site_id;
UPDATE ticket SET asl_old = site_id;
UPDATE access SET asl_old = site_id;
UPDATE comuni SET codiceistatasl_old = codiceistatas;
UPDATE comuni SET codice_old = codice;
UPDATE allerte_asl_coinvolte SET id_asl_old = id_asl;
UPDATE allerte_asl_coinvolte_history SET asl_old = asl;
UPDATE allerte_imprese_coinvolte SET idasl_old = idasl;
UPDATE blackberry_devices SET id_asl_old = id_asl;
UPDATE blackberry_logs SET id_asl_old = id_asl;
UPDATE contact SET site_id_old = site_id;
UPDATE cu_programmazioni_asl SET id_asl_old = id_asl;
UPDATE decessi_capi SET asl_old = asl;
UPDATE document_store_user_member SET site_id_old = site_id;
UPDATE farmacosorveglianza_allegatoi SET id_asl_old = id_asl;
UPDATE farmacosorveglianza_allegatoii SET id_asl_old = id_asl;
UPDATE furti_capi SET asl_old = asl;
UPDATE import SET site_id_old = site_id;
UPDATE informazioni_sanitarie_allevamenti SET asl_old = asl;
UPDATE ingresso_capi SET asl_old = asl;
UPDATE log SET asl_id_old = asl_id;
UPDATE log_operazioni SET asl_id_old = asl_id;
UPDATE m_capi SET cd_asl_old = cd_asl;
UPDATE macellazione_capi SET asl_old = asl;
UPDATE operatori_allevamenti SET id_asl_old = id_asl;
UPDATE organization_scadenzario SET site_id_old = site_id;
UPDATE uscita_capi  SET asl_old = asl;

-- fine --

-- update campi asl già esistenti per adeguamento alla nuova gestione asl --

UPDATE organization SET site_id = 201 where site_id in (1,2);
UPDATE organization SET site_id = 202 where site_id in (3);
UPDATE organization SET site_id = 203 where site_id in (4,5);
UPDATE organization SET site_id = 204 where site_id in (6);
UPDATE organization SET site_id = 205 where site_id in (7,8);
UPDATE organization SET site_id = 206 where site_id in (9,10);
UPDATE organization SET site_id = 207 where site_id in (11,12,13);

UPDATE ticket SET site_id = 201 where site_id in (1,2);
UPDATE ticket SET site_id = 202 where site_id in (3);
UPDATE ticket SET site_id = 203 where site_id in (4,5);
UPDATE ticket SET site_id = 204 where site_id in (6);
UPDATE ticket SET site_id = 205 where site_id in (7,8);
UPDATE ticket SET site_id = 206 where site_id in (9,10);
UPDATE ticket SET site_id = 207 where site_id in (11,12,13);

UPDATE access SET site_id = 201 where site_id in (1,2);
UPDATE access SET site_id = 202 where site_id in (3);
UPDATE access SET site_id = 203 where site_id in (4,5);
UPDATE access SET site_id = 204 where site_id in (6);
UPDATE access SET site_id = 205 where site_id in (7,8);
UPDATE access SET site_id = 206 where site_id in (9,10);
UPDATE access SET site_id = 207 where site_id in (11,12,13);

update comuni set codice = substring(codice from 1 for 4)||'201'||substring(codice from 8 for 6) where codiceistatasl in ('101','102');
update comuni set codice = substring(codice from 1 for 4)||'202'||substring(codice from 8 for 6) where codiceistatasl in ('103');
update comuni set codice = substring(codice from 1 for 4)||'203'||substring(codice from 8 for 6) where codiceistatasl in ('104', '105');
update comuni set codice = substring(codice from 1 for 4)||'204'||substring(codice from 8 for 6) where codiceistatasl in ('106');
update comuni set codice = substring(codice from 1 for 4)||'205'||substring(codice from 8 for 6) where codiceistatasl in ('107', '108');
update comuni set codice = substring(codice from 1 for 4)||'206'||substring(codice from 8 for 6) where codiceistatasl in ('109', '110');
update comuni set codice = substring(codice from 1 for 4)||'207'||substring(codice from 8 for 6) where codiceistatasl in ('111', '112', '113');

UPDATE comuni SET codiceistatasl = '201' where codiceistatasl in ('101','102');
UPDATE comuni SET codiceistatasl = '202' where codiceistatasl in ('103');
UPDATE comuni SET codiceistatasl = '203' where codiceistatasl in ('104','105');
UPDATE comuni SET codiceistatasl = '204' where codiceistatasl in ('106');
UPDATE comuni SET codiceistatasl = '205' where codiceistatasl in ('107','108');
UPDATE comuni SET codiceistatasl = '206' where codiceistatasl in ('109','110');
UPDATE comuni SET codiceistatasl = '207' where codiceistatasl in ('111','112','113');

UPDATE allerte_asl_coinvolte SET id_asl_old = 201 where id_asl_old in (1,2);
UPDATE allerte_asl_coinvolte SET id_asl_old = 202 where id_asl_old in (3);
UPDATE allerte_asl_coinvolte SET id_asl_old = 203 where id_asl_old in (4,5);
UPDATE allerte_asl_coinvolte SET id_asl_old = 204 where id_asl_old in (6);
UPDATE allerte_asl_coinvolte SET id_asl_old = 205 where id_asl_old in (7,8);
UPDATE allerte_asl_coinvolte SET id_asl_old = 206 where id_asl_old in (9,10);
UPDATE allerte_asl_coinvolte SET id_asl_old = 207 where id_asl_old in (11,12,13);

UPDATE allerte_asl_coinvolte_history SET asl = 201 where asl in (1,2);
UPDATE allerte_asl_coinvolte_history SET asl = 202 where asl in (3);
UPDATE allerte_asl_coinvolte_history SET asl = 203 where asl in (4,5);
UPDATE allerte_asl_coinvolte_history SET asl = 204 where asl in (6);
UPDATE allerte_asl_coinvolte_history SET asl = 205 where asl in (7,8);
UPDATE allerte_asl_coinvolte_history SET asl = 206 where asl in (9,10);
UPDATE allerte_asl_coinvolte_history SET asl = 207 where asl in (11,12,13);

UPDATE allerte_imprese_coinvolte SET idasl = 201 where idasl in (1,2);
UPDATE allerte_imprese_coinvolte SET idasl = 202 where idasl in (3);
UPDATE allerte_imprese_coinvolte SET idasl = 203 where idasl in (4,5);
UPDATE allerte_imprese_coinvolte SET idasl = 204 where idasl in (6);
UPDATE allerte_imprese_coinvolte SET idasl = 205 where idasl in (7,8);
UPDATE allerte_imprese_coinvolte SET idasl = 206 where idasl in (9,10);
UPDATE allerte_imprese_coinvolte SET idasl = 207 where idasl in (11,12,13);

UPDATE blackberry_devices SET id_asl = 201 where id_asl in (1,2);
UPDATE blackberry_devices SET id_asl = 202 where id_asl in (3);
UPDATE blackberry_devices SET id_asl = 203 where id_asl in (4,5);
UPDATE blackberry_devices SET id_asl = 204 where id_asl in (6);
UPDATE blackberry_devices SET id_asl = 205 where id_asl in (7,8);
UPDATE blackberry_devices SET id_asl = 206 where id_asl in (9,10);
UPDATE blackberry_devices SET id_asl = 207 where id_asl in (11,12,13);

UPDATE blackberry_logs SET id_asl = 201 where id_asl in (1,2);
UPDATE blackberry_logs SET id_asl = 202 where id_asl in (3);
UPDATE blackberry_logs SET id_asl = 203 where id_asl in (4,5);
UPDATE blackberry_logs SET id_asl = 204 where id_asl in (6);
UPDATE blackberry_logs SET id_asl = 205 where id_asl in (7,8);
UPDATE blackberry_logs SET id_asl = 206 where id_asl in (9,10);
UPDATE blackberry_logs SET id_asl = 207 where id_asl in (11,12,13);

UPDATE contact SET site_id = 201 where site_id in (1,2);
UPDATE contact SET site_id = 202 where site_id in (3);
UPDATE contact SET site_id = 203 where site_id in (4,5);
UPDATE contact SET site_id = 204 where site_id in (6);
UPDATE contact SET site_id = 205 where site_id in (7,8);
UPDATE contact SET site_id = 206 where site_id in (9,10);
UPDATE contact SET site_id = 207 where site_id in (11,12,13);

UPDATE cu_programmazioni_asl SET id_asl = 201 where id_asl in (1,2);
UPDATE cu_programmazioni_asl SET id_asl = 202 where id_asl in (3);
UPDATE cu_programmazioni_asl SET id_asl = 203 where id_asl in (4,5);
UPDATE cu_programmazioni_asl SET id_asl = 204 where id_asl in (6);
UPDATE cu_programmazioni_asl SET id_asl = 205 where id_asl in (7,8);
UPDATE cu_programmazioni_asl SET id_asl = 206 where id_asl in (9,10);
UPDATE cu_programmazioni_asl SET id_asl = 207 where id_asl in (11,12,13);

UPDATE decessi_capi SET asl = 201 where asl in (1,2);
UPDATE decessi_capi SET asl = 202 where asl in (3);
UPDATE decessi_capi SET asl = 203 where asl in (4,5);
UPDATE decessi_capi SET asl = 204 where asl in (6);
UPDATE decessi_capi SET asl = 205 where asl in (7,8);
UPDATE decessi_capi SET asl = 206 where asl in (9,10);
UPDATE decessi_capi SET asl = 207 where asl in (11,12,13);

UPDATE document_store_user_member SET site_id = 201 where site_id in (1,2);
UPDATE document_store_user_member SET site_id = 202 where site_id in (3);
UPDATE document_store_user_member SET site_id = 203 where site_id in (4,5);
UPDATE document_store_user_member SET site_id = 204 where site_id in (6);
UPDATE document_store_user_member SET site_id = 205 where site_id in (7,8);
UPDATE document_store_user_member SET site_id = 206 where site_id in (9,10);
UPDATE document_store_user_member SET site_id = 207 where site_id in (11,12,13);

UPDATE farmacosorveglianza_allegatoi SET id_asl = 201 where id_asl in (1,2);
UPDATE farmacosorveglianza_allegatoi SET id_asl = 202 where id_asl in (3);
UPDATE farmacosorveglianza_allegatoi SET id_asl = 203 where id_asl in (4,5);
UPDATE farmacosorveglianza_allegatoi SET id_asl = 204 where id_asl in (6);
UPDATE farmacosorveglianza_allegatoi SET id_asl = 205 where id_asl in (7,8);
UPDATE farmacosorveglianza_allegatoi SET id_asl = 206 where id_asl in (9,10);
UPDATE farmacosorveglianza_allegatoi SET id_asl = 207 where id_asl in (11,12,13);

UPDATE farmacosorveglianza_allegatoii SET id_asl = 201 where id_asl in (1,2);
UPDATE farmacosorveglianza_allegatoii SET id_asl = 202 where id_asl in (3);
UPDATE farmacosorveglianza_allegatoii SET id_asl = 203 where id_asl in (4,5);
UPDATE farmacosorveglianza_allegatoii SET id_asl = 204 where id_asl in (6);
UPDATE farmacosorveglianza_allegatoii SET id_asl = 205 where id_asl in (7,8);
UPDATE farmacosorveglianza_allegatoii SET id_asl = 206 where id_asl in (9,10);
UPDATE farmacosorveglianza_allegatoii SET id_asl = 207 where id_asl in (11,12,13);

UPDATE furti_capi SET asl = 201 where asl in (1,2);
UPDATE furti_capi SET asl = 202 where asl in (3);
UPDATE furti_capi SET asl = 203 where asl in (4,5);
UPDATE furti_capi SET asl = 204 where asl in (6);
UPDATE furti_capi SET asl = 205 where asl in (7,8);
UPDATE furti_capi SET asl = 206 where asl in (9,10);
UPDATE furti_capi SET asl = 207 where asl in (11,12,13);

UPDATE import SET site_id = 201 where site_id in (1,2);
UPDATE import SET site_id = 202 where site_id in (3);
UPDATE import SET site_id = 203 where site_id in (4,5);
UPDATE import SET site_id = 204 where site_id in (6);
UPDATE import SET site_id = 205 where site_id in (7,8);
UPDATE import SET site_id = 206 where site_id in (9,10);
UPDATE import SET site_id = 207 where site_id in (11,12,13);

UPDATE informazioni_sanitarie_allevamenti SET asl = 201 where asl in (1,2);
UPDATE informazioni_sanitarie_allevamenti SET asl = 202 where asl in (3);
UPDATE informazioni_sanitarie_allevamenti SET asl = 203 where asl in (4,5);
UPDATE informazioni_sanitarie_allevamenti SET asl = 204 where asl in (6);
UPDATE informazioni_sanitarie_allevamenti SET asl = 205 where asl in (7,8);
UPDATE informazioni_sanitarie_allevamenti SET asl = 206 where asl in (9,10);
UPDATE informazioni_sanitarie_allevamenti SET asl = 207 where asl in (11,12,13);

UPDATE ingresso_capi SET asl = 201 where asl in (1,2);
UPDATE ingresso_capi SET asl = 202 where asl in (3);
UPDATE ingresso_capi SET asl = 203 where asl in (4,5);
UPDATE ingresso_capi SET asl = 204 where asl in (6);
UPDATE ingresso_capi SET asl = 205 where asl in (7,8);
UPDATE ingresso_capi SET asl = 206 where asl in (9,10);
UPDATE ingresso_capi SET asl = 207 where asl in (11,12,13);

UPDATE log SET asl_id = 201 where asl_id in (1,2);
UPDATE log SET asl_id = 202 where asl_id in (3);
UPDATE log SET asl_id = 203 where asl_id in (4,5);
UPDATE log SET asl_id = 204 where asl_id in (6);
UPDATE log SET asl_id = 205 where asl_id in (7,8);
UPDATE log SET asl_id = 206 where asl_id in (9,10);
UPDATE log SET asl_id = 207 where asl_id in (11,12,13);

UPDATE log_operazioni SET asl_id = 201 where asl_id in (1,2);
UPDATE log_operazioni SET asl_id = 202 where asl_id in (3);
UPDATE log_operazioni SET asl_id = 203 where asl_id in (4,5);
UPDATE log_operazioni SET asl_id = 204 where asl_id in (6);
UPDATE log_operazioni SET asl_id = 205 where asl_id in (7,8);
UPDATE log_operazioni SET asl_id = 206 where asl_id in (9,10);
UPDATE log_operazioni SET asl_id = 207 where asl_id in (11,12,13);

UPDATE m_capi SET cd_asl = 201 where cd_asl in (1,2);
UPDATE m_capi SET cd_asl = 202 where cd_asl in (3);
UPDATE m_capi SET cd_asl = 203 where cd_asl in (4,5);
UPDATE m_capi SET cd_asl = 204 where cd_asl in (6);
UPDATE m_capi SET cd_asl = 205 where cd_asl in (7,8);
UPDATE m_capi SET cd_asl = 206 where cd_asl in (9,10);
UPDATE m_capi SET cd_asl = 207 where cd_asl in (11,12,13);

UPDATE macellazione_capi SET asl = 201 where asl in (1,2);
UPDATE macellazione_capi SET asl = 202 where asl in (3);
UPDATE macellazione_capi SET asl = 203 where asl in (4,5);
UPDATE macellazione_capi SET asl = 204 where asl in (6);
UPDATE macellazione_capi SET asl = 205 where asl in (7,8);
UPDATE macellazione_capi SET asl = 206 where asl in (9,10);
UPDATE macellazione_capi SET asl = 207 where asl in (11,12,13);

UPDATE operatori_allevamenti SET id_asl = 201 where id_asl in (1,2);
UPDATE operatori_allevamenti SET id_asl = 202 where id_asl in (3);
UPDATE operatori_allevamenti SET id_asl = 203 where id_asl in (4,5);
UPDATE operatori_allevamenti SET id_asl = 204 where id_asl in (6);
UPDATE operatori_allevamenti SET id_asl = 205 where id_asl in (7,8);
UPDATE operatori_allevamenti SET id_asl = 206 where id_asl in (9,10);
UPDATE operatori_allevamenti SET id_asl = 207 where id_asl in (11,12,13);

UPDATE organization_scadenzario SET site_id = 201 where site_id in (1,2);
UPDATE organization_scadenzario SET site_id = 202 where site_id in (3);
UPDATE organization_scadenzario SET site_id = 203 where site_id in (4,5);
UPDATE organization_scadenzario SET site_id = 204 where site_id in (6);
UPDATE organization_scadenzario SET site_id = 205 where site_id in (7,8);
UPDATE organization_scadenzario SET site_id = 206 where site_id in (9,10);
UPDATE organization_scadenzario SET site_id = 207 where site_id in (11,12,13);

UPDATE uscita_capi SET asl = 201 where asl in (1,2);
UPDATE uscita_capi SET asl = 202 where asl in (3);
UPDATE uscita_capi SET asl = 203 where asl in (4,5);
UPDATE uscita_capi SET asl = 204 where asl in (6);
UPDATE uscita_capi SET asl = 205 where asl in (7,8);
UPDATE uscita_capi SET asl = 206 where asl in (9,10);
UPDATE uscita_capi SET asl = 207 where asl in (11,12,13);

-- fine --

-- fine gestione pregresso --

/* Per la gestione del nuovo elenco ASL occorre aggiungere record alla tabella lookup_site_id */
 
INSERT INTO lookup_site_id(code, description, short_description, default_item, "level", enabled, codiceistat, code_canina)
    VALUES (201, 'AVELLINO', 'AV', FALSE, 1, TRUE, 201, 0);

INSERT INTO lookup_site_id(code, description, short_description, default_item, "level", enabled, codiceistat, code_canina)
    VALUES (202, 'BENEVENTO', 'BN', FALSE, 2, TRUE, 202, 0);

INSERT INTO lookup_site_id(code, description, short_description, default_item, "level", enabled, codiceistat, code_canina)
    VALUES (203, 'CASERTA', 'CE', FALSE, 3, TRUE, 203, 0);

INSERT INTO lookup_site_id(code, description, short_description, default_item, "level", enabled, codiceistat, code_canina)
    VALUES (204, 'NAPOLI 1 CENTRO', 'NA1 CENTRO', FALSE, 4, TRUE, 204, 0);

INSERT INTO lookup_site_id(code, description, short_description, default_item, "level", enabled, codiceistat, code_canina)
    VALUES (205, 'NAPOLI 2 NORD', 'NA2 NORD', FALSE, 5, TRUE, 205, 0);

INSERT INTO lookup_site_id(code, description, short_description, default_item, "level", enabled, codiceistat, code_canina)
    VALUES (206, 'NAPOLI 3 SUD', 'NA3 SUD', FALSE, 6, TRUE, 206, 0);

INSERT INTO lookup_site_id(code, description, short_description, default_item, "level", enabled, codiceistat, code_canina)
    VALUES (207, 'SALERNO', 'SA', FALSE, 7, TRUE, 207, 0);

-- fine aggiornamento tabella lookup_site_id --

/* Siccome le vecchie ASL non dovranno esser più visualizzate occorre settare il campo enabled in lookup_site_id a FALSE */

UPDATE lookup_site_id SET enabled = FALSE WHERE code <= 13;

-- fine script oscuramento vecchie ASL --

/*gestione ASL in procedure import SOA stabilimenti ed allevamenti*/


